package berlin.reiche.scheduler.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.scheduler.model.Course;

/**
 * The course schedule for a certain room for a whole week.
 * 
 * @author Konrad Reiche
 * 
 */
class RoomSchedule {

    /**
     * The schedule maps each day of the week to a list of time slots, where
     * each time slot can represent the beginning of a scheduled course.
     */
    Map<Day, Map<TimeSlot, Course>> schedule;

    /**
     * Initializes the room schedule based on the provided {@link Day} list and
     * {@link TimeSlot} list.
     * 
     * @param days
     *            the list of the days available for the room schedule.
     * @param timeSlots
     *            the list of time slots available for the room schedule.
     */
    public RoomSchedule(Collection<Day> days, Collection<TimeSlot> timeSlots) {
        super();
        schedule = new HashMap<>();

        for (Day day : days) {

            Map<TimeSlot, Course> daySchedule = new HashMap<>();
            for (TimeSlot timeSlot : timeSlots) {
                daySchedule.put(timeSlot, null);
            }
            schedule.put(day, daySchedule);
        }

    }

    /**
     * Schedules a certain course to a specific position in the room schedule.
     * This method should only be called from the {@link CourseSchedule} class.
     * 
     * @param course
     *            the course to schedule.
     * @param day
     *            the day specifying the position in the course schedule.
     * @param timeSlot
     *            the time slit specifying the position in the course schedule.
     */
    void setCourse(Course course, Day day, TimeSlot timeSlot) {

        schedule.get(day).put(timeSlot, course);
    }

    /**
     * @return the number of days available.
     */
    public int getDayCount() {
        return schedule.size();
    }

    /**
     * @param day
     *            the day of which the number of time slots should be returned.
     * @return the number of time slots available for a certain day.
     */
    public int getTimeSlotCount(Day day) {

        Map<TimeSlot, Course> daySchedule = schedule.get(day);
        if (daySchedule == null) {
            return 0;
        } else {
            return daySchedule.size();
        }
    }

    /**
     * @return all courses which are scheduled in this room schedule.
     */
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();

        for (Map<TimeSlot, Course> daySchedule : schedule.values()) {
            courses.addAll(daySchedule.values());
        }

        return courses;
    }
}
