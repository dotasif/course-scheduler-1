package berlin.reiche.virginia.scheduler;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.User;

/**
 * Stores information about a scheduled time slot.
 * 
 * @author Konrad Reiche
 * 
 */
public class ScheduleInformation {

    Course course;
    User lecturer;

    public ScheduleInformation(Course course, User lecturer) {
        super();
        this.course = course;
        this.lecturer = lecturer;
    }

}
