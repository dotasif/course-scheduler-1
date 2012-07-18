package berlin.reiche.virginia;

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

import org.bson.types.ObjectId;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.User;
import berlin.reiche.virginia.scheduler.CourseSchedule;

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
    private static final String LIST_SITE = "ftl/modules/list.ftl";
    private static final String FORM_SITE = "ftl/modules/form.ftl";
    private static final String COURSES_SITE = "ftl/modules/course-list.ftl";
    private static final String RESPONSIBLITIES_SITE = "ftl/modules/responsibilities.ftl";

    /**
     * Further constants.
     */
    private static final String SELECTED_USER = "user";

    /**
     * Singleton instance.
     */
    private static final ModuleServlet INSTANCE = new ModuleServlet();

    public final static String root = "/modules";

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

        if (path == null) {
            showCourseModules(response);
        } else if (path.equals("/")) {
            response.sendRedirect("/modules");
        } else if (path.matches("/" + AppServlet.ID_REGEX)) {
            ObjectId moduleId = new ObjectId(path.substring("/".length()));
            showCourses(response, moduleId);
        } else if (path.equals("/new")) {
            data.put(AppServlet.REQUEST_HEADLINE_VAR, "New Course Module");
            data.put("module", CourseModule.NULL_MODULE);
            AppServlet.processTemplate(FORM_SITE, data, writer);
        } else if (path.matches("/delete/" + AppServlet.ID_REGEX)) {
            ObjectId id = new ObjectId(path.substring("/delete/".length()));
            MongoDB.delete(CourseModule.class, id);
            MongoDB.delete(CourseSchedule.class);
            response.sendRedirect("/modules");
        } else if (path.matches("/edit/" + AppServlet.ID_REGEX)) {
            ObjectId id = new ObjectId(path.substring("/edit/".length()));
            CourseModule module = MongoDB.get(CourseModule.class, id);
            data.put("module", module);
            data.put(AppServlet.REQUEST_HEADLINE_VAR, "Edit Course Module");
            AppServlet.processTemplate(FORM_SITE, data, response.getWriter());
        } else if (path.equals("/responsibilities")) {
            List<CourseModule> modules = MongoDB.getAll(CourseModule.class);
            List<User> lecturers = MongoDB.createQuery(User.class)
                    .filter("lecturer =", true).asList();

            String selectedUser = request.getParameter(SELECTED_USER);
            if (selectedUser == null) {
                selectedUser = AppServlet.getCurrentUser(request).getName();
            }
            User user = MongoDB.get(User.class, selectedUser);
            data.put("user", user);
            data.put("modules", modules);
            data.put("lecturers", lecturers);
            AppServlet.processTemplate(RESPONSIBLITIES_SITE, data, writer);
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

        String path = request.getServletPath() + request.getPathInfo();

        if ("/modules/new".equals(path)) {
            handleModuleForm(request, response, null);
        } else if (path.matches("/modules/edit/" + AppServlet.ID_REGEX)) {
            ObjectId id = new ObjectId(
                    path.substring("/modules/edit/".length()));
            CourseModule module = MongoDB.get(CourseModule.class, id);
            handleModuleForm(request, response, module);
        } else if (path.equals("/modules/responsibilities")) {

            String[] ids = request.getParameterValues("responsibility");
            String selectedUser = request.getParameter(SELECTED_USER);
            if (selectedUser == null) {
                selectedUser = AppServlet.getCurrentUser(request).getName();
            }
            User user = MongoDB.get(User.class, selectedUser);
            user.getResponsibleCourses().clear();

            if (ids != null) {
                for (String id : ids) {
                    Course course = MongoDB.get(Course.class, new ObjectId(id));
                    user.addCourse(course);
                }
            }
            MongoDB.store(user);
            response.sendRedirect(path + "?user=" + selectedUser);
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
            courseModuleData.put("assessment", module.getAssessment());
            courseModuleData
                    .put("credits", String.valueOf(module.getCredits()));
            courseModuleDataList.add(courseModuleData);
        }
        data.put("modules", courseModuleDataList);
        AppServlet.processTemplate(LIST_SITE, data, response.getWriter());
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
        AppServlet.processTemplate(COURSES_SITE, data, response.getWriter());
    }

    /**
     * Handles a course module creation and modification request.
     * 
     * @param request
     *            provides request information for HTTP servlets.
     * @param response
     *            provides HTTP-specific functionality in sending a response.
     * @param oldModule
     *            The course module object if it is present, if it is present
     *            this is an entity modification request, else it is an entity
     *            creation request.
     * @throws IOException
     *             if an input or output exception occurs.
     */
    private void handleModuleForm(HttpServletRequest request,
            HttpServletResponse response, CourseModule oldModule)
            throws IOException {

        Map<String, Object> data = AppServlet.getDefaultData();

        String name = request.getParameter("name");
        int credits = Integer.valueOf(request.getParameter("credits"));
        String assessment = request.getParameter("assessment");
        CourseModule newModule = new CourseModule(name, credits, assessment);
        data.put("module", newModule);

        // Course types
        String[] types = request.getParameterValues("type");

        // Course durations
        String[] durations = request.getParameterValues("duration");

        // Course counts
        String[] counts = request.getParameterValues("count");

        // Number of equipment requirements for each course
        String[] equipmentCounts = request
                .getParameterValues("equipment-count");
        
        // Equipment names
        String[] equipments = request.getParameterValues("equipment");
        
        // Different quantities for the equipment requirements for all courses
        String[] equipmentQuantities = request.getParameterValues("quantity");

        int k = 0;
        List<Course> courses = new ArrayList<>();
        
        // For each defined course
        for (int i = 0; i < types.length; i++) {

            int duration = Integer.valueOf(durations[i]);
            int count= Integer.valueOf(counts[i]);
            Course course = new Course(types[i], duration, count);
            
            int equipmentCount = Integer.valueOf(equipmentCounts[i]);
            for (int j = 0; j < equipmentCount; j++) {

                int quantity = 0;
                if (!equipmentQuantities[k].equals("")) {
                    quantity = Integer.valueOf(equipmentQuantities[k]);
                }

                String constraint = equipments[k].replaceAll("[\n\r]", "");
                if (quantity > 0) {
                    course.getEquipment().put(constraint, quantity);
                } else {
                    course.getEquipment().remove(constraint);
                }
                
                k++;
            }
            courses.add(course);
        }

        String submitReason = request.getParameter("submit-reason");
        if (submitReason.equals("New Course")) {

            newModule.getCourses().addAll(courses);
            newModule.getCourses().add(Course.NULL_COURSE);
            String requestHeadline = "New Course Module";
            if (oldModule != null) {
                requestHeadline = "Edit Course Module";
            }

            data.put(AppServlet.REQUEST_HEADLINE_VAR, requestHeadline);
            AppServlet.processTemplate(FORM_SITE, data, response.getWriter());

        } else if (submitReason.equals("Create")) {

            if (oldModule == null) {
                oldModule = newModule;
            } else {
                oldModule.setName(name);
                oldModule.setCredits(credits);
                oldModule.setAssessment(assessment);
                oldModule.getCourses().clear();
            }

            MongoDB.store(oldModule);
            oldModule.getCourses().addAll(courses);
            for (Course course : courses) {
                course.setModule(oldModule);
                MongoDB.store(course);
            }
            MongoDB.store(oldModule);
            response.sendRedirect("/modules");
        } else {
            throw new IllegalStateException(
                    "An unknown submit value was received: " + submitReason);
        }
    }

    /**
     * @return an singleton instance of {@link ModuleServlet}.
     */
    public static ModuleServlet getInstance() {
        return INSTANCE;
    }

}
