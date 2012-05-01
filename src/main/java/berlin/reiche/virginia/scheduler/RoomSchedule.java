package berlin.reiche.virginia.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.TimeSlot;
import berlin.reiche.virginia.model.Timeframe;

import com.google.code.morphia.annotations.Embedded;

/**
 * The course schedule for a certain room for a whole week.
 * 
 * @author Konrad Reiche
 * 
 */
@Embedded
class RoomSchedule {

    /**
     * Null course for the schedule initialization.
     */
    private static final Course NULL_COURSE = new NullCourse();

    /**
     * Maps each day of the week (number) to a list of time slots, where each
     * time slot (number) can represent the beginning of a scheduled course.
     */
    @Embedded
    Map<Integer, Map<Integer, Course>> schedule;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private RoomSchedule() {

    }

    /**
     * Initializes the room schedule based on the provided {@link Day} list and
     * {@link TimeSlot} list.
     * 
     */
    public RoomSchedule(Timeframe timeframe) {
        super();
        schedule = new HashMap<>();
        for (int day = 0; day < timeframe.getDays(); day++) {
            Map<Integer, Course> daySchedule = new HashMap<>();
            for (int timeSlot = 0; timeSlot < timeframe.getTimeSlots(); timeSlot++) {
                daySchedule.put(timeSlot, NULL_COURSE);
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
    void setCourse(Course course, int day, int timeSlot) {

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
    public int getTimeSlotCount(int day) {

        Map<Integer, Course> daySchedule = schedule.get(day);
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

        for (Map<Integer, Course> daySchedule : schedule.values()) {
            for (Course course : daySchedule.values()) {
                if (course != null) {
                    courses.add(course);
                }
            }
        }

        return courses;
    }
}
