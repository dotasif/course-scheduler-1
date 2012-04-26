package berlin.reiche.virginia.scheduler;

import java.util.concurrent.Callable;

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
     * @param data
     *            the scheduling data defines the algorithm input.
     * @return a course schedule.
     */
    CourseSchedule schedule(ScheduleData data);
}
