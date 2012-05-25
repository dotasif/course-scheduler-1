package berlin.reiche.virginia.scheduler;

import java.util.List;

import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.model.User;

/**
 * The input data for the course scheduling algorithm.
 * 
 * @author Konrad Reiche
 *
 */
public class InputData {

    /**
     * The course modules to schedule.
     */
    List<CourseModule> modules;
    
    /**
     * The available rooms in which the courses can take take place.
     */
    List<Room> rooms;
    
    /**
     * The timeframe defining the time space in which courses can take place.
     */
    Timeframe timeframe;

    /**
     * The list of available lecturers.
     */
    List<User> lecturers;
    
}
