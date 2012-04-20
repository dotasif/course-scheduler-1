package berlin.reiche.scheduler.scheduler;

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

    public Scheduler() {
        exec = Executors.newSingleThreadExecutor();
    }

    /**
     * Schedules all available course modules on all available rooms.
     */
     
    public void schedule() {

        ScheduleData data = new ScheduleData();
        data.modules = MongoDB.getAll(CourseModule.class);
        data.rooms = MongoDB.getAll(Room.class);
        data.timeframe = MongoDB.getAll(Timeframe.class).get(0);

        ScheduleTask task = new ScheduleTask(data);
        exec.submit(task);

        try {
            CourseSchedule result = task.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
