package berlin.reiche.virginia.scheduler;

import java.util.concurrent.FutureTask;

/**
 * A {@link FutureTask} specialized for the course scheduling.
 * 
 * The task is based on the {@link GreedyAlgorithm}.
 * 
 * @author Konrad Reiche
 * 
 */
public class ScheduleTask extends FutureTask<CourseSchedule> {

    public ScheduleTask(ScheduleData data) {
        super(new GreedyAlgorithm(data));
    }

}
