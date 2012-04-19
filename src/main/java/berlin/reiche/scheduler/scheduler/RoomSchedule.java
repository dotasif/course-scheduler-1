package berlin.reiche.scheduler.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.scheduler.model.Course;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * The course schedule for a certain room for a whole week.
 * 
 * @author Konrad Reiche
 * 
 */
class RoomSchedule {

	/**
	 * The schedule maps each day of the week to a list of time slots, where
	 * each time slot can represent the beginning of a scheduled course.
	 */
	Map<Day, Map<TimeSlot, Course>> schedule;

	/**
	 * Initializes an empty room schedule based on the provided
	 * {@link Timeframe}.
	 * 
	 * @param timeframe
	 *            the timeframe which specifies the structure of the room
	 *            schedule.
	 */
	public RoomSchedule(Timeframe timeframe) {
		super();
		schedule = new HashMap<>();

		for (int i = 0; i < timeframe.days; i++) {
			Day day = new Day(i);
			Map<TimeSlot, Course> daySchedule = new HashMap<>();
			schedule.put(day, daySchedule);

			for (int j = 0; j < timeframe.timeSlots; j++) {
				TimeSlot timeSlot = new TimeSlot(j);
				daySchedule.put(timeSlot, null);
			}
		}
	}

	/**
	 * @return the number of days available.
	 */
	public int getDayCount() {
		return schedule.size();
	}

	/**
	 * @param day
	 *            the day of which the number of time slots should be returned.
	 * @return the number of time slots available for a certain day.
	 */
	public int getTimeSlotCount(Day day) {

		Map<TimeSlot, Course> daySchedule = schedule.get(day);
		if (daySchedule == null) {
			return 0;
		} else {
			return daySchedule.size();
		}
	}

	/**
	 * @return all courses which are scheduled in this room schedule.
	 */
	public List<Course> getCourses() {
		List<Course> courses = new ArrayList<>();

		for (Map<TimeSlot, Course> daySchedule : schedule.values()) {
			courses.addAll(daySchedule.values());
		}

		return courses;
	}
}
