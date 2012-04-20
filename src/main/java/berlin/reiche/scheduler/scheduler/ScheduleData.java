package berlin.reiche.scheduler.scheduler;

import java.util.List;

import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * The input data for the course scheduling algorithm.
 * 
 * @author Konrad Reiche
 *
 */
public class ScheduleData {

    List<CourseModule> modules;
    
    List<Room> rooms;
    
    Timeframe timeframe;
    
}
