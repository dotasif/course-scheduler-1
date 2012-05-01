package berlin.reiche.virginia.scheduler;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import berlin.reiche.virginia.MongoDB;
import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;

/**
 * The schedule is the component which is used to control the schedule and
 * launch the scheduler tasks.
 * 
 * @author Konrad Reiche
 * 
 */
public class Scheduler {

    private final ExecutorService exec;

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

        ScheduleData data = new ScheduleData();
        data.modules = MongoDB.getAll(CourseModule.class);
        data.rooms = MongoDB.getAll(Room.class);
        data.timeframe = MongoDB.getAll(Timeframe.class).get(0);

        if (!isScheduleable(data.modules, data.rooms, data.timeframe)) {
            throw new SchedulerException("Schedule Data is not scheduleable.");
        }

        ScheduleTask task = new ScheduleTask(data);
        exec.submit(task);

        try {
            CourseSchedule result = task.get();        
            MongoDB.deleteAll(CourseSchedule.class);
            MongoDB.store(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return whether there are enough resources to satisfy all the
     *         requirements for a desired course schedule.
     */
    public boolean isScheduleable(List<CourseModule> modules, List<Room> rooms,
            Timeframe timeframe) {

        int totalCourseTime = 0;
        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {
                totalCourseTime += course.getDuration() * course.getCount();
            }
        }

        boolean hasAvailableRooms = rooms.size() > 0;

        boolean fitsTimeframe = totalCourseTime <= timeframe.getDays()
                * timeframe.getTimeSlots() * rooms.size();

        return hasAvailableRooms && fitsTimeframe;
    }

}
