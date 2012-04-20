package berlin.reiche.scheduler.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * A timeframe defines the time which is available for the course scheduling.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("timeframe")
public class Timeframe {

    @Id
    ObjectId id;
    
    /**
     * The number of days available on a week for course scheduling.
     */
    int days;

    /**
     * The number of time slots available on a day for course scheduling.
     */
    int timeSlots;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private Timeframe() {

    }

    public Timeframe(int days, int timeSlots) {
        super();

        if (days <= 0 || timeSlots <= 0) {
            throw new IllegalStateException("The number of days"
                    + " or time slots have a non-positive value.");
        }

        this.days = days;
        this.timeSlots = timeSlots;
    }

    public int getDays() {
        return days;
    }

    public int getTimeSlots() {
        return timeSlots;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setTimeSlots(int timeSlots) {
        this.timeSlots = timeSlots;
    }

}
