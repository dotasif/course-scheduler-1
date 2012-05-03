package berlin.reiche.virginia.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Reference;

/**
 * Contains information about where and when one single course is scheduled.
 * 
 * @author Konrad Reiche
 * 
 */
@Embedded("schedule_entry")
public class ScheduleEntry {

    @Reference
    Course course;

    @Reference
    Room room;

    int day;

    int timeSlot;

    @SuppressWarnings("unused")
    private ScheduleEntry() {

    }

    public ScheduleEntry(Course course, Room room, int day, int timeSlot) {
        this.course = course;
        this.room = room;
        this.day = day;
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
    }

    public Room getRoom() {
        return room;
    }

    public int getDay() {
        return day;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

}