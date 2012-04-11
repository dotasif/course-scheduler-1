package berlin.reiche.scheduler.scheduler;

import java.util.List;

import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * An algorithms which takes a set of course modules and a set of rooms as input and
 * returns a room mapping for each course, hence a course a schedule.
 * 
 * @author Konrad Reiche
 * 
 */
public interface Algorithm extends Runnable {
	
	CourseSchedule schedule(Timeframe timeframe, List<CourseModule> modules, List<Room> rooms);
}
