package berlin.reiche.virginia;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import berlin.reiche.virginia.model.Equipment;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.model.User;
import berlin.reiche.virginia.servlets.AppServlet;
import berlin.reiche.virginia.servlets.EquipmentServlet;
import berlin.reiche.virginia.servlets.ModuleServlet;
import berlin.reiche.virginia.servlets.RoomServlet;
import berlin.reiche.virginia.servlets.SchedulerServlet;
import berlin.reiche.virginia.servlets.TimeframeServlet;
import berlin.reiche.virginia.servlets.UserServlet;

/**
 * @author Konrad Reiche
 * 
 */
public class Main {

    /**
     * The default port for the web server.
     */
    private static int port = 8080;

    /**
     * Path to the server property file.
     */
    private static final String SERVER_PROPERTIES_PATH = "site/resources/server.properties";

    /**
     * Path to the scheduler property file.
     */
    private static final String SCHEDULER_PROPERTIES_PATH = "site/resources/scheduler.properties";

    /**
     * Real name of the login service.
     */
    private static final String REALM_NAME = "User Realm";

    /**
     * The Jetty HTTP Servlet Server.
     */
    static Server server;

    /**
     * Launches the web server.
     * 
     * @throws IOException
     *             if there is a problem with the server properties file.
     */
    public static void main(String... args) throws IOException {

        checkDatabaseConnectivity();
        checkServerProperties();
        checkSchedulerProperties();
        server = new Server(port);

        try {
            ServletContextHandler context = new ServletContextHandler(
                    ServletContextHandler.SESSIONS
                            | ServletContextHandler.SECURITY);

            context.setInitParameter(
                    "org.eclipse.jetty.servlet.SessionIdPathParameterName",
                    "none");

            context.setContextPath("/");
            context.setSecurityHandler(setUpSecurityHandler());
            AppServlet.getInstance().setLoginService(
                    (HashLoginService) context.getSecurityHandler()
                            .getLoginService());

            context.addServlet(new ServletHolder(AppServlet.getInstance()),
                    "/*");
            context.addServlet(new ServletHolder(UserServlet.getInstance()),
                    "/users/*");
            context.addServlet(new ServletHolder(ModuleServlet.getInstance()),
                    "/modules/*");
            context.addServlet(new ServletHolder(RoomServlet.getInstance()),
                    "/rooms/*");
            context.addServlet(
                    new ServletHolder(EquipmentServlet.getInstance()),
                    "/equipment/*");
            context.addServlet(
                    new ServletHolder(TimeframeServlet.getInstance()),
                    "/timeframe/*");
            context.addServlet(
                    new ServletHolder(SchedulerServlet.getInstance()),
                    "/scheduler/*");

            ContextHandler fileHandler = new ContextHandler();
            fileHandler.setContextPath("/resources");
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("site/resources");
            fileHandler.setHandler(resourceHandler);

            ContextHandlerCollection contexts = new ContextHandlerCollection();
            contexts.setHandlers(new Handler[] { context, fileHandler });
            server.setHandler(contexts);

            server.start();

        } catch (Exception e) {
            System.err.println("Server failed to start.");
            e.printStackTrace();
        }
    }

    /**
     * For implementing an authentication and authorization service a security
     * handler is configured to protect the servlets from illegal access.
     * 
     * All paths besides the login and error page are protected. In order to
     * allow users to register themselves in a separate form a relaxation
     * constraint is added to make the path to the sign up form available for
     * non-authenticated user.
     * 
     * @return the configured security handler.
     */
    private static SecurityHandler setUpSecurityHandler() {

        HashLoginService loginService = new HashLoginService();
        loginService.setName(REALM_NAME);
        for (User user : MongoDB.getAll(User.class)) {
            BCryptPassword password = new BCryptPassword(user.getPassword());
            loginService.putUser(user.getLogin(), password, User.ROLES);
        }

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__FORM_AUTH);
        constraint.setRoles(User.ROLES);
        constraint.setAuthenticate(true);

        ConstraintMapping cm = new ConstraintMapping();
        cm.setConstraint(constraint);
        cm.setPathSpec("/*");

        Constraint relaxation = new Constraint();
        relaxation.setName(Constraint.ANY_ROLE);
        relaxation.setAuthenticate(false);

        ConstraintMapping rm = new ConstraintMapping();
        rm.setConstraint(relaxation);
        rm.setPathSpec("/signup");

        ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
        csh.setAuthenticator(new FormAuthenticator("/login", "/login/error",
                false));

        csh.setRealmName(REALM_NAME);
        csh.addConstraintMapping(cm);
        csh.addConstraintMapping(rm);
        csh.setLoginService(loginService);

        return csh;
    }

    /**
     * Applies the server configuration as defined in a file.
     * 
     * @throws IOException
     *             if an error occurred with the server property file.
     */
    private static void checkServerProperties() throws IOException {

        Properties serverProperties = new Properties();
        FileInputStream input = new FileInputStream(SERVER_PROPERTIES_PATH);
        serverProperties.load(input);
        port = Integer.valueOf(serverProperties.getProperty("server.port"));
        String adminLogin = serverProperties.getProperty("admin.login");
        String adminPassword = serverProperties.getProperty("admin.password");

        User admin = MongoDB.get(User.class, adminLogin);
        if (admin == null) {
            admin = new User(adminLogin, adminPassword, "Administrator",
                    "admin@admin.com", false, false);
            MongoDB.store(admin);
            System.out.println("Created default admin user.");
        }

        if (!admin.checkPassword(adminPassword)) {
            MongoDB.delete(User.class, adminLogin);
            admin = new User(adminLogin, adminPassword, "Administrator",
                    "admin@admin.com", false, false);
            MongoDB.store(admin);
            System.out.println("Assigned a new admin password.");
        }
    }

    /**
     * Applies the scheduler configuration as defined in a file.
     * 
     * @throws IOException
     *             if an error occurred with the scheduler property file.
     */
    private static void checkSchedulerProperties() throws IOException {

        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(SCHEDULER_PROPERTIES_PATH);
        properties.load(input);

        if (MongoDB.getAll(Timeframe.class).isEmpty()) {

            int days = Integer
                    .valueOf(properties.getProperty("timeframe.days"));
            int timeSlots = Integer.valueOf(properties
                    .getProperty("timeframe.timeSlots"));
            int startHour = Integer.valueOf(properties
                    .getProperty("timeframe.startHour"));
            String[] weekdays = properties.getProperty("timeframe.weekdays")
                    .replace(" ", "").split(",");

            Timeframe timeframe = new Timeframe(days, timeSlots, startHour,
                    Arrays.asList(weekdays));
            MongoDB.store(timeframe);
            System.out.println("Created default timeframe.");
        }

        if (MongoDB.getAll(Equipment.class).isEmpty()) {

            String[] items = properties.getProperty("equipment")
                    .replace(" ", "").split(",");
            Equipment equipment = new Equipment(items);
            MongoDB.store(equipment);
            System.out.println("Created default equipment");
        }
    }

    /**
     * The web application requires a working database connection. Thus it is
     * checked and if problem occurs the application has to be shut down.
     * 
     * @throws IOException
     *             if an error occurred with the scheduler property file.
     */
    private static void checkDatabaseConnectivity() throws IOException {

        Properties serverProperties = new Properties();
        FileInputStream input = new FileInputStream(SERVER_PROPERTIES_PATH);
        serverProperties.load(input);
        int port = Integer.valueOf(serverProperties.getProperty("server.port"));

        if (!MongoDB.isConnected()) {
            System.err.println("No MongoDB running on localhost:" + port);
            System.err.println("Application is shut down.");
            System.exit(-1);
        }

    }
}
