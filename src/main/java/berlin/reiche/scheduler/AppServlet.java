package berlin.reiche.scheduler;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {

	private static AppServlet instance = new AppServlet();
	
	private AppServlet() {
		
	}
	
	public static AppServlet getInstance() {
		return instance;
	}


}
