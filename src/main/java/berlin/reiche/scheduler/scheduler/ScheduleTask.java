package berlin.reiche.scheduler.scheduler;

import java.util.List;
import java.util.concurrent.FutureTask;

import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * A {@link FutureTask} specialized for the course scheduling.
 * 
 * The task is based on the {@link GreedyAlgorithm}.
 * 
 * @author Konrad Reiche
 * 
 */
public class ScheduleTask extends FutureTask<CourseSchedule> {

    public ScheduleTask(Timeframe timeframe, List<CourseModule> modules,
            List<Room> rooms) {
        super(new GreedyAlgorithm(timeframe, modules, rooms));
    }

}
