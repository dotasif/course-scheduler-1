package berlin.reiche.scheduler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import berlin.reiche.scheduler.model.User;

/**
 * The room servlet which is dedicated for all requests regarding rooms.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class RoomServlet extends HttpServlet {

	/**
	 * File path to the web resources.
	 */

	private static final String ROOMS_SITE = "ftl/rooms/list.ftl";

	/**
	 * Singleton instance.
	 */
	private static RoomServlet instance = new RoomServlet();

	/**
	 * The constructor is private in order to enforce the singleton pattern.
	 */
	private RoomServlet() {

	}

	/**
	 * Parses the HTTP request and writes the response by using the template
	 * engine.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getPathInfo();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(AppServlet.LOGIN_ATTRIBUTE);
		if (user == null && !path.equals("/login")) {
			response.sendRedirect("/login");
			return;
		}

		Map<String, Object> data = AppServlet.getDefaultData();

		if (path == null) {
			AppServlet.processTemplate(ROOMS_SITE, data, response.getWriter());
		} else if (path.equals("/")) {
			response.sendRedirect("/rooms");
		}
	}

	/**
	 * @return a singleton instance of {@link RoomServlet}.
	 */
	public static RoomServlet getInstance() {
		return instance;
	}

}
