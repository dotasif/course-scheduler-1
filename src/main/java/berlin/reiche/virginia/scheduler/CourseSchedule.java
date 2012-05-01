package berlin.reiche.virginia.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

/**
 * A course schedule is a mapping of courses to a room and a time slots.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("schedule")
public class CourseSchedule {

    /**
     * The timeframe on which this course schedule is based.
     */
    Timeframe timeframe;

    /**
     * Each room has its own schedule for a whole week.
     */
    @Embedded
    Map<Room, RoomSchedule> schedules;

    
    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private CourseSchedule() {
        
    }
    
    public CourseSchedule(Timeframe timeframe, List<Room> rooms) {
        super();
        this.timeframe = timeframe;
        this.schedules = new HashMap<>();
        
        for (Room room : rooms) {
            schedules.put(room,
                    new RoomSchedule(timeframe));
        }
    }

    /**
     * Schedules a certain course to a specific position in the course schedule.
     * This method should be called from the course scheduling algorithms or the
     * interface to manually schedule courses.
     * 
     * @param course
     *            the course to schedule.
     * @param room
     *            the room in which the course should take place.
     * @param day
     *            the day specifying the position in the course schedule.
     * @param timeSlot
     *            the time slit specifying the position in the course schedule.
     */
    public void setCourse(Course course, Room room, int day, int timeSlot) {
        schedules.get(room).setCourse(course, day, timeSlot);
    }
    
    

}
