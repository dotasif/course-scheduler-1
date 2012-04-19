package berlin.reiche.scheduler.scheduler;

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

        CourseSchedule schedule = new CourseSchedule(timeframe, rooms);

        Room currentRoom = rooms.get(0);
        int currentTimeSlot = 0;
        int currentDay = 0;

        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {

                for (int i = 0; i < course.getCount(); i++) {

                    schedule.setCourse(course, currentRoom, currentDay,
                            currentTimeSlot);
                }

            }
        }

        return schedule;
    }
}
