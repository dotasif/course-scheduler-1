package berlin.reiche.scheduler.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * Each room has its own schedule for a whole week.
	 */
	Map<Room, RoomSchedule> schedules;

	public CourseSchedule(Timeframe timeframe, List<Room> rooms) {
		super();
		this.timeframe = timeframe;
		this.schedules = new HashMap<>();
		
		for (Room room : rooms) {
			schedules.put(room, new RoomSchedule(timeframe));
		}
	}

}
