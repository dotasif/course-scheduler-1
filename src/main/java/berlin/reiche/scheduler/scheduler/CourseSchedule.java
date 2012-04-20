package berlin.reiche.scheduler.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.scheduler.model.Course;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

/**
 * A course schedule is a mapping of courses to a room and a time slots.
 * 
 * @author Konrad Reiche
 * 
 */
public class CourseSchedule {

    /**
     * The list of the available day objects.
     */
    final Map<Integer, Day> days;

    /**
     * The list of the available timeSlot objects.
     */
    final Map<Integer, TimeSlot> timeSlots;

    /**
     * The timeframe on which this course schedule is based.
     */
    final Timeframe timeframe;

    /**
     * Each room has its own schedule for a whole week.
     */
    final Map<Room, RoomSchedule> schedules;

    public CourseSchedule(Timeframe timeframe, List<Room> rooms) {
        super();
        this.timeframe = timeframe;
        this.schedules = new HashMap<>();
        this.days = new HashMap<>();
        this.timeSlots = new HashMap<>();

        for (int i = 0; i < timeframe.getDays(); i++) {
            days.put(i, new Day(i));
        }

        for (int i = 0; i < timeframe.getTimeSlots(); i++) {
            timeSlots.put(i, new TimeSlot(i));
        }

        for (Room room : rooms) {

            schedules.put(room,
                    new RoomSchedule(days.values(), timeSlots.values()));
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

        Day dayObject = days.get(day);
        TimeSlot timeSlotObject = timeSlots.get(timeSlot);
        schedules.get(room).setCourse(course, dayObject, timeSlotObject);
    }

}
