package berlin.reiche.scheduler.scheduler;

import java.util.concurrent.FutureTask;

public class ScheduleTask extends FutureTask<CourseSchedule> {

	
	public ScheduleTask(Runnable runnable, CourseSchedule result) {
		super(runnable, result);
	}

}
