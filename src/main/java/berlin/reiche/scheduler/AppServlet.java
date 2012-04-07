package berlin.reiche.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
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
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The main servlet of the application which handles all incoming HTTP requests.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {

	/**
	 * File path to the web resources.
	 */
	private static final String WEB_PATH = "site/";

	private static final String LOGIN_SITE = "ftl/login.ftl";
	private static final String MAIN_SITE = "ftl/main.ftl";
	private static final String ERROR_SITE = "ftl/404.ftl";
	private static final String MODULES_SITE = "ftl/modules/list.ftl";
	private static final String MODULE_FORM_SITE = "ftl/modules/form.ftl";
	private static final String MODULE_COURSES_SITE = "ftl/modules/course-list.ftl";

	/**
	 * Further constants which appear more than one in the source code.
	 */
	private static final String LOGIN_ATTRIBUTE = "login.isLoggedIn";
	private static final String REQUEST_HEADLINE_VAR = "requestHeadline";

	private static final String DEFAULT_VALUES_PATH = "site/resources/default-values.properties";

	/**
	 * Regular expression for matching a course module id.
	 */
	private static final String ID_REGEX = "[a-f0-9]*";

	/**
	 * Singleton instance.
	 */
	private static AppServlet instance = new AppServlet();

	/**
	 * Configuration used for the Freemarker template processing.
	 */
	Configuration configuration;

	/**
	 * The constructor is private in order to enforce the singleton pattern. The
	 * configuration for the template engine Freemarker is set up with default
	 * settings.
	 */
	private AppServlet() {

		try {
			configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new File(WEB_PATH));
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		} catch (IOException e) {
			System.err.println("The path " + WEB_PATH
					+ " could not be retrieved.");
			e.printStackTrace();
		}
	}

	/**
	 * Processed the given template file with the provided data. The result is
	 * written immediately to the given writer.
	 * 
	 * @param relativePath
	 *            the relative path from the base directory pointing to the
	 *            template file.
	 * @param data
	 *            the root node of the data model.
	 * @param writer
	 *            the writer to which the processed template is written.
	 * @throws IOException
	 *             if an I/O exception occurs due to writing or flushing.
	 */
	private void processTemplate(String relativePath, Map<String, ?> data,
			Writer writer) throws IOException {

		try {
			Template template = configuration.getTemplate(relativePath);
			template.process(data, writer);
			writer.flush();
		} catch (TemplateException e) {
			System.err.println("The template" + relativePath
					+ "could not be processed properly.");
			e.printStackTrace();
		}
	}

	/**
	 * Parses the HTTP request and writes the response by using the template
	 * engine.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath() + request.getPathInfo();
		Map<String, Object> data = AppServlet.getDefaultData();
		Writer writer = response.getWriter();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(LOGIN_ATTRIBUTE);
		if (user == null && !path.equals("/login")) {
			response.sendRedirect("/login");
			return;
		}

		if (path.equals("/")) {
			processTemplate(MAIN_SITE, data, writer);
		} else if (path.equals("/login")) {

			if (user == null) {
				processTemplate(LOGIN_SITE, data, writer);
			} else {
				response.sendRedirect("/");
			}

		} else if (path.equals("/modules")) {
			showCourseModules(response);
		} else if (path.matches("/modules/" + ID_REGEX)) {
			ObjectId moduleId = new ObjectId(path.substring("/modules/"
					.length()));
			showCourses(response, moduleId);
		} else if (path.equals("/modules/new")) {
			data.put(REQUEST_HEADLINE_VAR, "New Course Module");
			data.put("blankCourse", true);
			processTemplate(MODULE_FORM_SITE, data, writer);
		} else if (path.matches("/modules/delete/" + ID_REGEX)) {
			ObjectId id = new ObjectId(path.substring("/modules/delete/"
					.length()));
			MongoDB.delete(CourseModule.class, id);
			response.sendRedirect("/modules");
		} else if (path.matches("/modules/edit/" + ID_REGEX)) {
			ObjectId id = new ObjectId(
					path.substring("/modules/edit/".length()));
			CourseModule module = MongoDB.get(CourseModule.class, id);
			handleModuleModification(request, response, module);
		} else {
			processTemplate(ERROR_SITE, data, writer);
		}
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

		Map<String, Object> data = getDefaultData();
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
		processTemplate(MODULE_COURSES_SITE, data, response.getWriter());
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

		Map<String, Object> data = getDefaultData();
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
		processTemplate(MODULES_SITE, data, response.getWriter());
	}

	/**
	 * Parses all user HTML form requests and handles them.
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath() + request.getPathInfo();

		if ("/login".equals(path)) {
			handleLoginRequest(request, response);
		} else if ("/modules/new".equals(path)) {
			handleModuleForm(request, response, null);
		} else if (path.matches("/modules/edit/" + ID_REGEX)) {
			ObjectId id = new ObjectId(
					path.substring("/modules/edit/".length()));
			CourseModule module = MongoDB.get(CourseModule.class, id);
			handleModuleForm(request, response, module);
		}
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

		Map<String, Object> data = getDefaultData();
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

			processTemplate(MODULE_FORM_SITE, data, response.getWriter());
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

		Map<String, Object> data = getDefaultData();
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
		processTemplate(MODULE_FORM_SITE, data, response.getWriter());
	}

	/**
	 * Handles a user submitted login request.
	 * 
	 * @param request
	 *            provides request information for HTTP servlets.
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void handleLoginRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, Object> data = getDefaultData();
		String login = request.getParameter("name");
		String password = request.getParameter("password");

		User user = MongoDB.get(User.class, login);
		if (user != null && user.checkPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute(LOGIN_ATTRIBUTE, user);
			response.sendRedirect("/");
		} else {
			data.put("hasLoginFailed", "true");
			processTemplate(LOGIN_SITE, data, response.getWriter());
		}
	}

	/**
	 * Generates a data model out of the default value property file.
	 * 
	 * @return the data model.
	 * @throws IOException
	 *             if the file with the default value properties could not been
	 *             found or an error occurred during reading from it.
	 * 
	 */
	private static Map<String, Object> getDefaultData() throws IOException {

		Map<String, Object> defaultData = new TreeMap<>();
		Properties defaultValues = new Properties();
		FileInputStream input = new FileInputStream(DEFAULT_VALUES_PATH);
		defaultValues.load(input);
		for (Entry<Object, Object> entry : defaultValues.entrySet()) {
			defaultData.put((String) entry.getKey(), (String) entry.getValue());
		}

		return defaultData;
	}

	/**
	 * @return an instance of {@link AppServlet}.
	 */
	public static AppServlet getInstance() {
		return instance;
	}

}
