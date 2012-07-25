package berlin.reiche.virginia.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import berlin.reiche.virginia.MongoDB;
import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.ScheduleEntry;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.model.User;

/**
 * The schedule is the component which is used to control the schedule and
 * launch the scheduler tasks.
 * 
 * @author Konrad Reiche
 * 
 */
public class Scheduler {

    final ExecutorService exec;

    public Scheduler() {
        exec = Executors.newSingleThreadExecutor();
    }

    /**
     * Schedules all available course modules on all available rooms.
     * 
     * @throws SchedulerException
     *             if the data is not scheduleable
     */

    public void schedule() throws SchedulerException {

        InputData data = new InputData();
        data.modules = MongoDB.getAll(CourseModule.class);
        data.rooms = MongoDB.getAll(Room.class);
        data.timeframe = MongoDB.getAll(Timeframe.class).get(0);
        data.lecturers = MongoDB.createQuery(User.class)
                .filter("lecturer =", true).asList();

        if (!isScheduleable(data.modules, data.lecturers, data.rooms,
                data.timeframe)) {
            throw new SchedulerException("Courses are not scheduleable.");
        }

        ScheduleTask task = new ScheduleTask(data);
        exec.submit(task);

        try {
            CourseSchedule result = task.get();
            MongoDB.deleteAll(CourseSchedule.class);
            MongoDB.deleteAll(ScheduleEntry.class);
            MongoDB.store(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param modules
     *            the course modules to schedule
     * @param lecturers
     *            the lecturers which can hold the courses
     * @param rooms
     *            the available rooms for courses to take place
     * @param timeframe
     *            the available timeframe to span for each room schedule.
     * @return whether there are enough resources to satisfy all the
     *         requirements for a desired course schedule.
     */
    public boolean isScheduleable(List<CourseModule> modules,
            List<User> lecturers, List<Room> rooms, Timeframe timeframe) {

        List<Course> coursesWithNoLecturer = new ArrayList<>();
        int totalCourseTime = 0;
        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {
                coursesWithNoLecturer.add(course);
                totalCourseTime += course.getDuration() * course.getCount();
            }
        }
        
        for (User lecturer : lecturers) {
            coursesWithNoLecturer.removeAll(lecturer.getResponsibleCourses());
        }
        
        if (coursesWithNoLecturer.size() > 0) {
            return false;
        }

        boolean hasAvailableRooms = rooms.size() > 0;

        boolean fitsTimeframe = totalCourseTime <= timeframe.getDays()
                * timeframe.getTimeSlots() * rooms.size();

        return hasAvailableRooms && fitsTimeframe;
    }

}
