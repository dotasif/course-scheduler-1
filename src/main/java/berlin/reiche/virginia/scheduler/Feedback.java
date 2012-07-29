package berlin.reiche.virginia.scheduler;

import java.util.ArrayList;
import java.util.List;

import berlin.reiche.virginia.model.Course;

/**
 * After a scheduling request was made this class will be used to inform the
 * user, whether the scheduling was successful and if not what the reasons for
 * the failure were.
 * 
 * @author Konrad Reiche
 * 
 */
public class Feedback {

    /**
     * The courses which do not have at least one responsible lecturer assigned.
     * The the list is empty then this is no fail reason.
     */
    List<Course> coursesLackingLecturer = new ArrayList<>();

    /**
     * Whether the course schedule data are lacking available rooms for course
     * to be held in.
     */
    boolean lackingRooms;

    /**
     * Whether the scheduling was successful.
     */
    boolean successful;

    /**
     * Whether all courses fit into the given timeframe. The timeframe is
     * ineligible, if the total course time (duration * count) is greater than
     * (time slots * days * rooms).
     */
    boolean timeframeIneligible;

    
    public List<Course> getCoursesLackingLecturer() {
        return coursesLackingLecturer;
    }

    public boolean isLackingRooms() {
        return lackingRooms;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public boolean isTimeframeIneligible() {
        return timeframeIneligible;
    }

    public void setLackingRooms(boolean lackingRooms) {
        this.lackingRooms = lackingRooms;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void setTimeframeIneligible(boolean timeframeIneligible) {
        this.timeframeIneligible = timeframeIneligible;
    }    

}
