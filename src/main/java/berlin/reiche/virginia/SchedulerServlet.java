package berlin.reiche.virginia;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.ScheduleEntry;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.scheduler.CourseSchedule;
import berlin.reiche.virginia.scheduler.ScheduleInformation;
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

    /**
     * Singleton instance.
     */
    private static final SchedulerServlet INSTANCE = new SchedulerServlet();

    /**
     * The scheduler object which is used to perform scheduling tasks.
     */
    private final Scheduler scheduler;

    public final static String root = "/scheduler";

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
        
        
                
        if (path == null) {
            showSchedule(request, response, data);
        } else if (path.equals("/")) {
            response.sendRedirect("/scheduler");
        } else if (path.equals("/success")) {
            data.put("successful", true);
            showSchedule(request, response, data);
        } else if (path.equals("/error")) {
            data.put("failed", true);
            showSchedule(request, response, data);
        } else if (path.equals("/start")) {
            try {
                scheduler.schedule();
                response.sendRedirect("/scheduler/success");
            } catch (SchedulerException e) {
                response.sendRedirect("/scheduler/error");
            }
        } else if (path.equals("/delete")) {
            MongoDB.deleteAll(CourseSchedule.class);
            MongoDB.deleteAll(ScheduleEntry.class);
            response.sendRedirect("/scheduler");
        } else {
            AppServlet.processTemplate(AppServlet.NOT_FOUND_SITE, data, writer);
        }
    }

    /**
     * Displays the control menu and the current course schedule.
     * 
     * @param request
     *            provides request information for HTTP servlets.
     * @param response
     *            provides HTTP-specific functionality in sending a response.
     * @throws IOException
     *             if an input or output exception occurs.
     */
    private void showSchedule(HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> data) throws IOException {

        data.put("hasSchedule", false);

        CourseSchedule schedule = MongoDB.get(CourseSchedule.class);
        if (schedule != null) {
            data.put("hasSchedule", true);
            List<Map<String, Object>> schedules = new ArrayList<>();
            
            Timeframe timeframe = schedule.getTimeframe();
            data.put("timeframe", timeframe);
            for (Room room : schedule.getRooms()) {
                Map<String, Object> scheduleData = new HashMap<>();
                scheduleData.put("room", room.toString());
                
                List<List<ScheduleInformation>> timeRows = new ArrayList<>();
                for (int i = 0; i < timeframe.getTimeSlots(); i++) {
                    List<ScheduleInformation> cells = new ArrayList<>();
                    for (int j = 0; j < timeframe.getDays(); j++) {
                        ScheduleInformation information = schedule.getScheduleInformation(room, j, i);
                        cells.add(information);
                    }
                    timeRows.add(cells);
                }
                scheduleData.put("timeRows", timeRows);
                schedules.add(scheduleData);
            }
            
            data.put("schedules", schedules);
        }

        AppServlet.processTemplate(SCHEDULER_SITE, data, response.getWriter());
    }

    /**
     * @return a singleton instance of {@link RoomServlet}.
     */
    public static SchedulerServlet getInstance() {
        return INSTANCE;
    }

}
