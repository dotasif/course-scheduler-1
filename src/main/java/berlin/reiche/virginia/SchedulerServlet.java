package berlin.reiche.virginia;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import berlin.reiche.virginia.model.User;
import berlin.reiche.virginia.scheduler.Scheduler;
import berlin.reiche.virginia.scheduler.SchedulerException;

/**
 * The scheduler servlet is dedicated to to control the scheduler.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class SchedulerServlet extends HttpServlet {

	/**
	 * File path to the web resources.
	 */
	private static final String SCHEDULER_SITE = "ftl/scheduler/control.ftl";
	private static final String RESULT_SITE = "ftl/scheduler/result.ftl";

	/**
	 * Singleton instance.
	 */
	private static final SchedulerServlet INSTANCE = new SchedulerServlet();


	/**
	 * The scheduler object which is used to perform scheduling tasks.
	 */
	private final Scheduler scheduler;

	/**
	 * The constructor is private in order to enforce the singleton pattern.
	 */
	private SchedulerServlet() {
		scheduler = new Scheduler();
	}

	/**
	 * Parses the HTTP request and writes the response by using the template
	 * engine.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getPathInfo();

		Map<String, Object> data = AppServlet.getDefaultData();
		Writer writer = response.getWriter();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(AppServlet.LOGIN_ATTRIBUTE);
		if (user == null) {
			response.sendRedirect("/login");
			return;
		}

		if (path == null) {
			AppServlet.processTemplate(SCHEDULER_SITE, data, writer);
		} else if (path.equals("/")) {
			response.sendRedirect("/scheduler");
		} else if (path.equals("/start")) {

			try {
				scheduler.schedule();
				AppServlet.processTemplate(RESULT_SITE, data, writer);
			} catch (SchedulerException e) {
				data.put("reason", e.getMessage());
				AppServlet.processTemplate(AppServlet.ERROR_SITE, data, writer);
			}

		} else {
			AppServlet.processTemplate(AppServlet.NOT_FOUND_SITE, data, writer);
		}
	}

	/**
	 * Parses all user HTML form requests and handles them.
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @return a singleton instance of {@link RoomServlet}.
	 */
	public static SchedulerServlet getInstance() {
		return INSTANCE;
	}

}