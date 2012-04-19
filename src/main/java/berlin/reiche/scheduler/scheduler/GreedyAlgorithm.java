package berlin.reiche.scheduler.scheduler;

import java.util.List;

import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * A course scheduling algorithms which is based on a greedy strategy.
 * 
 * @author Konrad Reiche
 * 
 */
public class GreedyAlgorithm implements Algorithm {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public CourseSchedule schedule(Timeframe timeframe,
			List<CourseModule> modules, List<Room> rooms) {

		CourseSchedule schedule = new CourseSchedule(timeframe, rooms);

		return schedule;
	}
}
