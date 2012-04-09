package berlin.reiche.scheduler;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;

import berlin.reiche.scheduler.model.Course;
import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.User;

/**
 * The main servlet of the application which handles all incoming HTTP requests.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class ModuleServlet extends HttpServlet {

	/**
	 * File path to the web resources.
	 */
	private static final String ERROR_SITE = "ftl/404.ftl";
	private static final String MODULES_SITE = "ftl/modules/list.ftl";
	private static final String MODULE_FORM_SITE = "ftl/modules/form.ftl";
	private static final String MODULE_COURSES_SITE = "ftl/modules/course-list.ftl";

	/**
	 * Further constants which appear more than one in the source code.
	 */
	private static final String LOGIN_ATTRIBUTE = "login.isLoggedIn";
	private static final String REQUEST_HEADLINE_VAR = "requestHeadline";

	/**
	 * Regular expression for matching a course module id.
	 */
	private static final String ID_REGEX = "[a-f0-9]*";

	/**
	 * Singleton instance.
	 */
	private static ModuleServlet instance = new ModuleServlet();

	/**
	 * The constructor is private in order to enforce the singleton pattern.
	 */
	private ModuleServlet() {

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
		User user = (User) session.getAttribute(LOGIN_ATTRIBUTE);
		if (user == null && !path.equals("/login")) {
			response.sendRedirect("/login");
			return;
		}

		if (path == null) {
			showCourseModules(response);
		} else if (path.equals("/")) {
			response.sendRedirect("/modules");
		} else if (path.matches("/" + ID_REGEX)) {
			ObjectId moduleId = new ObjectId(path.substring("/".length()));
			showCourses(response, moduleId);
		} else if (path.equals("/new")) {
			data.put(REQUEST_HEADLINE_VAR, "New Course Module");
			data.put("blankCourse", true);
			AppServlet.processTemplate(MODULE_FORM_SITE, data, writer);
		} else if (path.matches("/delete/" + ID_REGEX)) {
			ObjectId id = new ObjectId(path.substring("/delete/".length()));
			MongoDB.delete(CourseModule.class, id);
			response.sendRedirect("/modules");
		} else if (path.matches("/edit/" + ID_REGEX)) {
			ObjectId id = new ObjectId(path.substring("/edit/".length()));
			CourseModule module = MongoDB.get(CourseModule.class, id);
			handleModuleModification(request, response, module);
		} else {
			AppServlet.processTemplate(ERROR_SITE, data, writer);
		}
	}

	/**
	 * Parses all user HTML form requests and handles them.
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath() + request.getPathInfo();

		if ("/modules/new".equals(path)) {
			handleModuleForm(request, response, null);
		} else if (path.matches("/modules/edit/" + ID_REGEX)) {
			ObjectId id = new ObjectId(
					path.substring("/modules/edit/".length()));
			CourseModule module = MongoDB.get(CourseModule.class, id);
			handleModuleForm(request, response, module);
		}
	}

	/**
	 * Retrieves all course modules and displays them.
	 * 
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void showCourseModules(HttpServletResponse response)
			throws IOException {

		Map<String, Object> data = AppServlet.getDefaultData();
		List<Map<String, String>> courseModuleDataList = new ArrayList<>();
		for (CourseModule module : MongoDB.getAll(CourseModule.class)) {
			Map<String, String> courseModuleData = new TreeMap<>();
			courseModuleData.put("id", String.valueOf(module.getId()));
			courseModuleData.put("name", module.getName());
			courseModuleData.put("assessment", module.getAssessmentType());
			courseModuleData
					.put("credits", String.valueOf(module.getCredits()));
			courseModuleDataList.add(courseModuleData);
		}
		data.put("modules", courseModuleDataList);
		AppServlet.processTemplate(MODULES_SITE, data, response.getWriter());
	}

	/**
	 * Retrieves the courses of the given course module and displays them.
	 * 
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * 
	 * @param moduleId
	 *            the id identifying the course module.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void showCourses(HttpServletResponse response, ObjectId moduleId)
			throws IOException {

		Map<String, Object> data = AppServlet.getDefaultData();
		CourseModule module = MongoDB.get(CourseModule.class, moduleId);

		data.put("name", module.getName());
		List<Map<String, String>> courseDataList = new ArrayList<>();
		for (Course course : module.getCourses()) {
			Map<String, String> courseData = new TreeMap<>();
			courseData.put("type", course.getType());
			courseData.put("duration", String.valueOf(course.getDuration()));
			courseData.put("count", String.valueOf(course.getCount()));
			courseDataList.add(courseData);
		}
		data.put("courses", courseDataList);
		AppServlet.processTemplate(MODULE_COURSES_SITE, data,
				response.getWriter());
	}

	/**
	 * Handles a course module creation request.
	 * 
	 * @param request
	 *            provides request information for HTTP servlets.
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * @param module
	 *            The course module object if it is present, if it is present
	 *            this is an entity modification request, else it is an entity
	 *            creation request.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void handleModuleForm(HttpServletRequest request,
			HttpServletResponse response, CourseModule module)
			throws IOException {

		Map<String, Object> data = AppServlet.getDefaultData();
		List<Map<String, String>> courseDataList = new ArrayList<>();

		String name = request.getParameter("name");
		int credits = Integer.valueOf(request.getParameter("credits"));
		String assessment = request.getParameter("assessment");

		String[] courseTypes = request.getParameterValues("course-type");
		String[] courseDurations = request
				.getParameterValues("course-duration");
		String[] courseCounts = request.getParameterValues("course-count");

		String submitReason = request.getParameter("submit-reason");
		if (submitReason.equals("New Course")) {

			data.put("name", name);
			data.put("credits", credits);
			data.put("assessment", assessment);

			for (int i = 0; i < courseTypes.length; ++i) {
				Map<String, String> courseData = new TreeMap<>();
				courseData.put("type", courseTypes[i]);
				courseData.put("duration", courseDurations[i]);
				courseData.put("count", courseCounts[i]);
				courseDataList.add(courseData);
			}

			String requestHeadline = (module == null) ? "New Course Module"
					: "Edit Course Module";

			data.put("courses", courseDataList);
			data.put(REQUEST_HEADLINE_VAR, requestHeadline);
			data.put("blankCourse", true);

			AppServlet.processTemplate(MODULE_FORM_SITE, data,
					response.getWriter());
		} else if (submitReason.equals("Create")) {

			if (module == null) {
				module = new CourseModule(name, credits, assessment);
			} else {
				module.setName(name);
				module.setCredits(credits);
				module.setAssessmentType(assessment);
				module.getCourses().clear();
			}

			for (int i = 0; i < courseTypes.length; ++i) {
				Course course = new Course(courseTypes[i],
						Integer.valueOf(courseDurations[i]),
						Integer.valueOf(courseCounts[i]));
				module.getCourses().add(course);
			}

			MongoDB.store(module);
			response.sendRedirect("/modules");

		} else {
			throw new IllegalStateException(
					"An unknown submit value was received.");
		}
	}

	/**
	 * Handles a course module modification request.
	 * 
	 * @param request
	 *            provides request information for HTTP servlets.
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * @param module
	 *            the course module which is requested for modification.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void handleModuleModification(HttpServletRequest request,
			HttpServletResponse response, CourseModule module)
			throws IOException {

		Map<String, Object> data = AppServlet.getDefaultData();
		List<Map<String, Object>> courseDataList = new ArrayList<>();

		data.put("name", module.getName());
		data.put("credits", module.getCredits());
		data.put("assessment", module.getAssessmentType());

		for (Course course : module.getCourses()) {
			Map<String, Object> courseData = new TreeMap<>();
			courseData.put("type", course.getType());
			courseData.put("duration", course.getDuration());
			courseData.put("count", course.getCount());
			courseDataList.add(courseData);
		}

		data.put("courses", courseDataList);
		data.put(REQUEST_HEADLINE_VAR, "Edit Course Module");
		AppServlet
				.processTemplate(MODULE_FORM_SITE, data, response.getWriter());
	}

	/**
	 * @return an singleton instance of {@link ModuleServlet}.
	 */
	public static ModuleServlet getInstance() {
		return instance;
	}

}
