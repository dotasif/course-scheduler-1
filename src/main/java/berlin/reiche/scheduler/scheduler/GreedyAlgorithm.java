package berlin.reiche.scheduler.scheduler;

import java.util.ArrayList;
import java.util.List;

import berlin.reiche.scheduler.model.Course;
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

		// initialize the course schedule
		CourseSchedule schedule = new CourseSchedule(timeframe);
		for (Room room : rooms) {
			List<List<Course>> roomSchedule = new ArrayList<>();

			for (int i = 0; i < timeframe.days; i++) {
				roomSchedule.add(new ArrayList<Course>(timeframe.timeSlots));
			}

			schedule.schedules.put(room, roomSchedule);
		}

		int currentDay = 0;
		int currentTimeSlot = 0;
		int currentRoom = 0;
		Room room = rooms.get(currentRoom);

		// schedule the courses
		for (CourseModule module : modules) {
			for (Course course : module.getCourses()) {
								
				List<Course> daySchedule = schedule.schedules.get(room)
						.get(currentDay);

				while (true) {

					if (currentTimeSlot + course.getDuration() < timeframe.timeSlots) {
						break;
					} else if (currentDay == timeframe.days
							&& currentTimeSlot == timeframe.timeSlots) {
					
						currentRoom++;
						room = rooms.get(currentRoom);
						currentDay = 0;
						currentTimeSlot = 0;
						
					} else {
						currentDay++;
						currentTimeSlot = 0;
					}
				}
				
				for (int i = currentTimeSlot; i < currentTimeSlot + course.getDuration(); i++) {
					daySchedule.add(i, course);
					
				}
				
				currentTimeSlot += course.getDuration();
				
			}
		}

		return schedule;
	}
}
