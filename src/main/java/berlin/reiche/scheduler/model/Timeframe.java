package berlin.reiche.scheduler.model;

/**
 * A timeframe defines the time which is available for the course scheduling.
 * 
 * @author Konrad Reiche
 * 
 */
public class Timeframe {

    /**
     * The number of days available on a week for course scheduling.
     */
    public final int days;

    /**
     * The number of time slots available on a day for course scheduling.
     */
    public final int timeSlots;

    public Timeframe(int days, int timeSlots) {
        super();

        if (days <= 0 || timeSlots <= 0) {
            throw new IllegalStateException("The number of days"
                    + " or time slots have a non-positive value.");
        }

        this.days = days;
        this.timeSlots = timeSlots;
    }

}
