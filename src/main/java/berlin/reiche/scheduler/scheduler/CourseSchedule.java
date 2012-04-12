package berlin.reiche.scheduler.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.scheduler.model.Course;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * A course schedule is a mapping of courses to a room and a time slots.
 * 
 * @author Konrad Reiche
 * 
 */
public class CourseSchedule {

	/**
	 * The timeframe on which this course schedule is based.
	 */
	final Timeframe timeframe;

	/**
	 * Each room has its own timetable. A timetable is a list of days, where
	 * each day is a list of time slots which are empty (null) or filled with a
	 * {@link Course}.
	 */
	Map<Room, List<List<Course>>> schedules;

	public CourseSchedule(Timeframe timeframe) {
		super();
		this.timeframe = timeframe;
		this.schedules = new HashMap<>();
	}

}
