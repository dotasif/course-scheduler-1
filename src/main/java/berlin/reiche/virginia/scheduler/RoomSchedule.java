package berlin.reiche.virginia.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.TimeSlot;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.model.User;

/**
 * The course schedule for a certain room for a whole week.
 * 
 * @author Konrad Reiche
 * 
 */
class RoomSchedule {

    /**
     * Maps each day of the week (number) to a list of time slots, where each
     * time slot (number) can represent the beginning of a scheduled course.
     */
    Map<Integer, Map<Integer, ScheduleInformation>> schedule;

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
            Map<Integer, ScheduleInformation> daySchedule = new HashMap<>();
            for (int timeSlot = 0; timeSlot < timeframe.getTimeSlots(); timeSlot++) {
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
     * @param lecturer
     *            the lecturer which helds the course.
     * @param day
     *            the day specifying the position in the course schedule.
     * @param timeSlot
     *            the time slit specifying the position in the course schedule.
     */
    void setCourse(Course course, User lecturer, int day, int timeSlot) {
        ScheduleInformation information = new ScheduleInformation(course,
                lecturer);
        schedule.get(day).put(timeSlot, information);
    }

    /**
     * Unschedules a certain course from the specific position in the room
     * schedule. This class this could only be called from the
     * {@link CourseSchedule} class.
     * 
     * @param lecturer
     *            the lecturer which helds the course.
     * @param day
     *            the day specifying the position in the course schedule.
     * @param timeSlot
     *            the time slit specifying the position in the course schedule.
     */
    void unsetCourse(User lecturer, int day, int timeSlot) {
        schedule.get(day).remove(timeSlot);
    }

    /**
     * Retrieves a certain course from the specific position in the room
     * schedule.
     * 
     * @param day
     *            the day specifying the position in the course schedule.
     * @param timeSlot
     *            the time slit specifying the position in the course schedule.
     */
    Course getCourse(int day, int timeSlot) {
        ScheduleInformation information = schedule.get(day).get(timeSlot);
        if (information == null) {
            return null;
        } else {
            return information.course;
        }
    }

    /**
     * Retrieves a certain course from the specific position in the room
     * schedule.
     * 
     * @param day
     *            the day specifying the position in the course schedule.
     * @param timeSlot
     *            the time slit specifying the position in the course schedule.
     */
    ScheduleInformation getScheduleInformation(int day, int timeSlot) {
        return schedule.get(day).get(timeSlot);
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

        Map<Integer, ScheduleInformation> daySchedule = schedule.get(day);
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

        for (Map<Integer, ScheduleInformation> daySchedule : schedule.values()) {
            for (ScheduleInformation information : daySchedule.values()) {
                if (information != null) {
                    courses.add(information.course);
                }
            }
        }

        return courses;
    }

}
