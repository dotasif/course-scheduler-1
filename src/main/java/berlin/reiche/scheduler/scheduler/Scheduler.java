package berlin.reiche.scheduler.scheduler;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import berlin.reiche.scheduler.MongoDB;
import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * The schedule is the component which is used to control the schedule and
 * launch the scheduler tasks.
 * 
 * @author Konrad Reiche
 * 
 */
public class Scheduler {

    private final ExecutorService exec;
    private final Timeframe timeframe;

    public Scheduler() {
        exec = Executors.newSingleThreadExecutor();
        timeframe = new Timeframe(5, 12); // TODO: Implement a servlet module
                                          // for this
    }

    /**
     * Schedules all available course modules on all available rooms.
     */
    public void schedule() {

        // TODO: Encapsulate the parameters as algorithm
        List<CourseModule> modules = MongoDB.getAll(CourseModule.class);
        List<Room> rooms = MongoDB.getAll(Room.class);

        ScheduleTask task = new ScheduleTask(timeframe, modules, rooms);
        exec.submit(task);

        try {
            CourseSchedule result = task.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
