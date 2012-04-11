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
	 * The number of hours available on a day for course scheduling.
	 */
	public final int hours;

	
	public Timeframe(int days, int hours) {
		super();
		this.days = days;
		this.hours = hours;
	}

}
