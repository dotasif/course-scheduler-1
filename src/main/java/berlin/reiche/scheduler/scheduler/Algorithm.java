package berlin.reiche.scheduler.scheduler;

/**
 * An algorithms which takes a set of courses and a set of rooms as input and
 * returns a room mapping for each course, hence a course a schedule.
 * 
 * @author Konrad Reiche
 * 
 */
public interface Algorithm {
	
	CourseSchedule schedule();
}
