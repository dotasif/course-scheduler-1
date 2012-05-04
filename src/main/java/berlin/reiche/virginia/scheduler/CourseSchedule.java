package berlin.reiche.virginia.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.ScheduleEntry;
import berlin.reiche.virginia.model.Timeframe;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Transient;

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
     * Each room has its own schedule for a whole week. This data is not
     * persisted to the database, since a {@link Map} with custom class keys are
     * not supported by Morphia for now.
     */
    @Transient
    private Map<Room, RoomSchedule> schedules;

    /**
     * The entries represent the course schedule, this data is redundant to
     * <code>schedules</code>, but will be persisted.
     */
    @Embedded
    List<ScheduleEntry> entries;

    /**
     * The list of rooms used in this course schedule in order to reinitialize
     * an instance of this class completely after it was instantiated by
     * Morphia.
     */
    @Reference
    List<Room> rooms;

    /**
     * Whether both data structures, schedules and entries, are initialized and
     * homogeneous.
     */
    @Transient
    boolean isInitialized;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private CourseSchedule() {
        this.schedules = new HashMap<>();
    }

    public CourseSchedule(Timeframe timeframe, List<Room> rooms) {
        super();
        this.timeframe = timeframe;
        this.schedules = new HashMap<>();
        this.entries = new ArrayList<>();
        this.rooms = new ArrayList<>(rooms);
        initialize();
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
        ScheduleEntry entry = new ScheduleEntry(course, room, day, timeSlot);
        entries.add(entry);
    }

    public Timeframe getTimeframe() {
        return timeframe;
    }

    public Map<Room, RoomSchedule> getSchedules() {
        return schedules;
    }

    /**
     * Initializes the present data structures of this class: the content of
     * <code>entries</code> is reflected to <code>schedules</code>.
     */
    @PostLoad
    public void initialize() {
        
        if (!isInitialized) {
            for (Room room : rooms) {
                schedules.put(room, new RoomSchedule(timeframe));
            }
            
            for (ScheduleEntry entry : entries) {
                Room room = entry.getRoom();
                Course course = entry.getCourse();
                int day = entry.getDay();
                int timeSlot = entry.getTimeSlot();
                schedules.get(room).setCourse(course, day, timeSlot);
            }

            isInitialized = true;
        }
    }

}
