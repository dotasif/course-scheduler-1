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

    /**
     * The executor service responsible for managing the scheduling tasks.
     */
    final ExecutorService exec;

    public Scheduler() {
        exec = Executors.newSingleThreadExecutor();
    }

    /**
     * Schedules all available course modules on all available rooms.
     * 
     * @return feedback about the scheduling attempt. Whether a course schedule
     *         could be computed and if not the reasons for the failure.
     */
    public Feedback schedule() {

        Feedback feedback = new Feedback();
        InputData data = new InputData();
        data.modules = MongoDB.getAll(CourseModule.class);
        data.rooms = MongoDB.getAll(Room.class);
        data.timeframe = MongoDB.getAll(Timeframe.class).get(0);
        data.lecturers = MongoDB.createQuery(User.class)
                .filter("lecturer =", true).asList();

        if (!isSchedulable(data, feedback)) {
            return feedback;
        }

        ScheduleTask task = new ScheduleTask(data);
        exec.submit(task);

        try {
            CourseSchedule schedule = task.get();
            MongoDB.deleteAll(CourseSchedule.class);
            MongoDB.deleteAll(ScheduleEntry.class);
            MongoDB.store(schedule);
            feedback.setSuccessful(true);
            return feedback;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assert(false);
        return null;
    }

    /**
     * Checks based on the given input data, whether the constraints can be met
     * by the given resources. Additional information about the lack of
     * resources is written into the {@link Feedback} object.
     * 
     * @param data
     *            the input data for the scheduling algorithm.
     * @param feedback
     *            the feedback for returning additional information about the
     *            schedulability.
     * @return whether the course data is schedulable.
     */
    public boolean isSchedulable(InputData data, Feedback feedback) {

        List<Course> coursesLackingLecturer = new ArrayList<>();
        int totalCourseTime = 0;
        for (CourseModule module : data.modules) {
            for (Course course : module.getCourses()) {
                coursesLackingLecturer.add(course);
                totalCourseTime += course.getDuration() * course.getCount();
            }
        }

        for (User lecturer : data.lecturers) {
            coursesLackingLecturer.removeAll(lecturer.getResponsibleCourses());
        }

        boolean hasLecturerCoverage = coursesLackingLecturer.size() == 0;
        feedback.getCoursesLackingLecturer().addAll(coursesLackingLecturer);

        boolean hasAvailableRooms = data.rooms.size() > 0;
        feedback.setLackingRooms(!hasAvailableRooms);

        Timeframe timeframe = data.timeframe;
        boolean fitsTimeframe = totalCourseTime <= timeframe.getDays()
                * timeframe.getTimeSlots() * data.rooms.size();
        feedback.setTimeframeIneligible(!fitsTimeframe);

        return hasLecturerCoverage && hasAvailableRooms && fitsTimeframe;
    }

}
