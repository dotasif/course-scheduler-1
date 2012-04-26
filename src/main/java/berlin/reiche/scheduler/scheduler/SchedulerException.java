package berlin.reiche.scheduler.scheduler;

/**
 * This exception indicates that something has gone wrong during the attempt to
 * schedule the data.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class SchedulerException extends Exception {

	public SchedulerException(String reason) {
		super(reason);
	}
	
}
