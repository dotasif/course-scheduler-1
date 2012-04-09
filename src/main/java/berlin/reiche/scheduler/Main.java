package berlin.reiche.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import berlin.reiche.scheduler.model.User;

/**
 * @author Konrad Reiche
 * 
 */
public class Main {

	/**
	 * The port for the web server.
	 */
	private static int port = 80;

	private static final String SERVER_PROPERTIES_PATH = "site/resources/server.properties";

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

		checkServerProperties();
		server = new Server(port);

		try {
			ServletContextHandler context = new ServletContextHandler(
					ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			server.setHandler(context);
			
			context.addServlet(new ServletHolder(AppServlet.getInstance()),
					"/*");
			context.addServlet(new ServletHolder(ModuleServlet.getInstance()),
					"/modules/*");
			context.addServlet(new ServletHolder(RoomServlet.getInstance()),
					"/rooms/*");

			server.start();

		} catch (Exception e) {
			System.err.println("Server failed to start.");
			e.printStackTrace();
		}
	}

	private static void checkServerProperties() throws IOException {

		Properties serverProperties = new Properties();
		FileInputStream input = new FileInputStream(SERVER_PROPERTIES_PATH);
		serverProperties.load(input);
		port = Integer.valueOf(serverProperties.getProperty("server.port"));
		String adminUsername = serverProperties.getProperty("admin.username");
		String adminPassword = serverProperties.getProperty("admin.password");

		User admin = MongoDB.get(User.class, adminUsername);
		if (admin == null) {
			System.out.println("Creating default admin user.");
			admin = new User(adminUsername, adminPassword, "", false, false);
			MongoDB.store(admin);
		}

		if (!admin.checkPassword(adminPassword)) {
			System.out.println("Assigning a new admin password.");
			MongoDB.delete(User.class, adminUsername);
			MongoDB.store(admin);
		}
	}
}
