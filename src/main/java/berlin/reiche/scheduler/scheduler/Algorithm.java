package berlin.reiche.scheduler.scheduler;

import java.util.List;
import java.util.concurrent.Callable;

import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * An algorithms which takes a set of course modules and a set of rooms as input
 * and returns a room mapping for each course, hence a course a schedule.
 * 
 * @author Konrad Reiche
 * 
 */
public interface Algorithm extends Callable<CourseSchedule> {

    /**
     * Schedules courses based on the provided input.
     * 
     * @param timeframe
     *            the timeframe defines the structure of the schedule.
     * @param modules
     *            the course modules which should be scheduled.
     * @param rooms
     *            the rooms available for the course scheduling.
     * @return the course schedule.
     */
    CourseSchedule schedule(Timeframe timeframe, List<CourseModule> modules,
            List<Room> rooms);
}
