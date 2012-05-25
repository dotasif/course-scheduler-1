package berlin.reiche.virginia.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.model.User;

/**
 * A course scheduling algorithms which is based on a greedy strategy.
 * 
 * @author Konrad Reiche
 * 
 */
public class GreedyAlgorithm implements Algorithm {

    /**
     * The whole schedule data.
     */
    private final InputData data;

    /**
     * the timeframe defines the structure of the schedule.
     */
    private final Timeframe timeframe;
    /**
     * The course modules which should be scheduled.
     */
    private final List<CourseModule> modules;
    /**
     * The rooms available for the course scheduling.
     */
    private final List<Room> rooms;

    /**
     * The list of available lecturers per course.
     */
    private final Map<Course, List<User>> responsibleLecturers;

    /**
     * Default constructor.
     * 
     * @param data
     *            the data which contains the scheduling information.
     */
    public GreedyAlgorithm(InputData data) {

        super();
        this.data = data;
        this.timeframe = data.timeframe;
        this.modules = data.modules;
        this.rooms = data.rooms;
        this.responsibleLecturers = new HashMap<>();
        for (User lecturer : data.lecturers) {
            for (Course course : lecturer.getResponsibleCourses()) {
                List<User> lecturers = responsibleLecturers.get(course);
                if (lecturers == null) {
                    lecturers = new ArrayList<>();
                    responsibleLecturers.put(course, lecturers);
                }
                lecturers.add(lecturer);
            }
        }
    }

    /**
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public CourseSchedule call() {
        return schedule(data);
    }

    /**
     * @see berlin.reiche.virginia.scheduler.Algorithm#schedule(berlin.reiche.virginia.scheduler.InputData)
     */
    @Override
    public CourseSchedule schedule(InputData data) {

        CourseSchedule schedule = new CourseSchedule(timeframe, rooms);

        int currentRoom = 0;
        int currentTimeSlot = 0;
        int currentDay = 0;
        Room room = rooms.get(currentRoom);

        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {
                
                User lecturer = responsibleLecturers.get(course).get(0);
                for (int i = 0; i < course.getCount(); i++) {
                    
                    if (currentTimeSlot + course.getDuration() > timeframe
                            .getTimeSlots()) {

                        currentDay++;
                        currentTimeSlot = 0;

                        if (currentDay == timeframe.getDays()) {
                            currentRoom++;
                            room = rooms.get(currentRoom);
                            currentDay = 0;
                        }
                    }

                    schedule.setCourse(course, lecturer, room, currentDay,
                            currentTimeSlot);
                    
                    currentTimeSlot += course.getDuration();
                }

            }
        }

        return schedule;
    }
}
