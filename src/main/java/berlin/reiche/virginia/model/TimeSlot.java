package berlin.reiche.virginia.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Represents a time slot in the course schedule.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("time_slot")
public class TimeSlot {

    /**
     * A number which identifies this time slot uniquely.
     */
    @Id
    int number;

    public TimeSlot(int number) {
        this.number = number;
    }

}
