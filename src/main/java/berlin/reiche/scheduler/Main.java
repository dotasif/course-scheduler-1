package berlin.reiche.scheduler;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author Konrad Reiche
 *
 */
public class Main {
	
	/**
	 * The port for the web server.
	 */
	private static final int PORT = 80;
	
	/**
	 * The Jetty HTTP Servlet Server.
	 */
	private static Server server;

	/**
	 * Launches the web server.
	 */
	public static void main(String... args) {
		
		server = new Server(PORT);
		try {		
		    ServletContextHandler context = new ServletContextHandler(
			    ServletContextHandler.SESSIONS);
		    context.setContextPath("/");
		    server.setHandler(context);
		    context.addServlet(new ServletHolder(new ApplicationServlet()), "/*");
		    
		    server.start();
		    
		} catch (Exception e) {
		    System.err.println("Component failed to start.");
		    e.printStackTrace();
		}
	}
}
