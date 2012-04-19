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

    /**
     * the timeframe defines the structure of the schedule.
     */
    private final Timeframe timeframe;
    /**
     * The course modules which should be scheduled.
     */
    private final List<CourseModule> modules;
    /**
     * The rooms available for the course scheduling.
     */
    private final List<Room> rooms;

    /**
     * Default constructor.
     * 
     * @param timeframe
     *            the timeframe defines the structure of the schedule.
     * @param modules
     *            the course modules which should be scheduled.
     * @param rooms
     *            the rooms available for the course scheduling.
     */
    public GreedyAlgorithm(Timeframe timeframe, List<CourseModule> modules,
            List<Room> rooms) {

        super();
        this.timeframe = timeframe;
        this.modules = modules;
        this.rooms = rooms;
    }

    /**
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public CourseSchedule call() {
        return schedule(timeframe, modules, rooms);
    }

    /**
     * @see berlin.reiche.scheduler.scheduler.Algorithm#schedule(berlin.reiche.scheduler.model.Timeframe,
     *      java.util.List, java.util.List)
     */
    @Override
    public CourseSchedule schedule(Timeframe timeframe,
            List<CourseModule> modules, List<Room> rooms) {

        CourseSchedule schedule = new CourseSchedule(timeframe, rooms);

        int currentRoom = 0;
        int currentTimeSlot = 0;
        int currentDay = 0;
        Room room = rooms.get(currentRoom);

        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {

                for (int i = 0; i < course.getCount(); i++) {

                    if (currentTimeSlot + course.getDuration() > timeframe.timeSlots) {

                        currentDay++;
                        currentTimeSlot = 0;

                        if (currentDay == timeframe.days) {
                            currentRoom++;
                            room = rooms.get(currentRoom);
                            currentDay = 0;
                        }

                    }

                    schedule.setCourse(course, room, currentDay,
                            currentTimeSlot);

                    currentTimeSlot += course.getDuration();
                }

            }
        }

        return schedule;
    }
}
