package berlin.reiche.scheduler.scheduler;

import java.util.List;
import java.util.Map;

import berlin.reiche.scheduler.model.Course;
import berlin.reiche.scheduler.model.Room;

/**
 * A course schedule is a mapping of courses to a room and a time slots.
 * 
 * @author Konrad Reiche
 * 
 */
public class CourseSchedule {

	/**
	 * Each room has its own timetable. A timetable is a list of days, where
	 * each day is a list of time slots which are empty (null) or filled with a
	 * {@link Course}.
	 */
	Map<Room, List<List<Course>>> schedule;
}
