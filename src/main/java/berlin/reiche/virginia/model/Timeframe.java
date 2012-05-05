package berlin.reiche.virginia.model;

import java.util.List;

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
	 * The hour on which the course day is starting.
	 */
	int startHour;
	
	/**
	 * The names of the weekdays.
	 */
	List<String> weekdays;

	/**
	 * This constructor is used by Morphia via Java reflections.
	 */
	@SuppressWarnings("unused")
	private Timeframe() {

	}

	public Timeframe(int days, int timeSlots, int startHour, List<String> weekdays) {
		super();

		if (days <= 0 || timeSlots <= 0 || startHour <= 0) {
			throw new IllegalStateException("The number of days"
					+ ", time slots or start hour have a non-positive value.");
		} else if (days != weekdays.size()) {
			throw new IllegalStateException("The number of days "
					+ "and the number of weekday names does not match.");
		}

		this.days = days;
		this.timeSlots = timeSlots;
		this.startHour = startHour;
		this.weekdays = weekdays;
	}

	public int getDays() {
		return days;
	}

	public int getStartHour() {
        return startHour;
    }

	public int getTimeSlots() {
		return timeSlots;
	}

	public List<String> getWeekdays() {
		return weekdays;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setTimeSlots(int timeSlots) {
		this.timeSlots = timeSlots;
	}

    public void setWeekdays(List<String> weekdays) {
		this.weekdays = weekdays;
	}
	
	

}
