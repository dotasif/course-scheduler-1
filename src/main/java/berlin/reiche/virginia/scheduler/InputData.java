package berlin.reiche.virginia.scheduler;

import java.util.List;

import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;

/**
 * The input data for the course scheduling algorithm.
 * 
 * @author Konrad Reiche
 *
 */
public class InputData {

    List<CourseModule> modules;
    
    List<Room> rooms;
    
    Timeframe timeframe;
    
}
