package berlin.reiche.virginia.scheduler;

import berlin.reiche.virginia.model.Course;

/**
 * The null course is an application of the Null Object Pattern. It is used for
 * the course schedule on time slots which have no course scheduled in order to
 * avoid database problems which would normally occur when storing the
 * <code>null</code> value.
 * 
 * @author Konrad Reiche
 * 
 */
public class NullCourse extends Course {

    /**
     * Default constructor.
     */
    public NullCourse() {
        super(null, 0, 0);
    }

}
