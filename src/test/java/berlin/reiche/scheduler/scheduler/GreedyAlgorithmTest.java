package berlin.reiche.scheduler.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import berlin.reiche.scheduler.model.Course;
import berlin.reiche.scheduler.model.CourseModule;
import berlin.reiche.scheduler.model.Room;
import berlin.reiche.scheduler.model.Timeframe;

public class GreedyAlgorithmTest {

    private static final String EXAM = "Exam";
    private static final String LECTURE = "Lecture";
    private static final String TUTORIAL = "Tutorial";
    private static final String SEMINAR_ROOM = "Seminar Room";

    List<CourseModule> modules = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    Timeframe timeframe = new Timeframe(5, 12);

    @Before
    public void setUp() {

        CourseModule module = new CourseModule("Advanced Algorithms", 8, EXAM);
        module.getCourses().add(new Course(LECTURE, 2, 2));
        module.getCourses().add(new Course(TUTORIAL, 2, 1));
        modules.add(module);

        Room room = new Room("SR001", SEMINAR_ROOM);
        rooms.add(room);
    }

    @Test
    public void testSchedule() {

        GreedyAlgorithm algorithm = new GreedyAlgorithm(timeframe, modules,
                rooms);
        
        List<Course> coursesNotScheduled = new ArrayList<>();
        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {
                for (int i = 0; i < course.getCount(); i++) {
                    coursesNotScheduled.add(course);
                }
            }
        }

        assertEquals(3, coursesNotScheduled.size());
        CourseSchedule schedule = algorithm.schedule(timeframe, modules, rooms);
        assertEquals(timeframe, schedule.timeframe);

        for (Room room : rooms) {
            assertNotNull(schedule.schedules.get(room));
            assertEquals(timeframe.getDays(), schedule.schedules.get(room)
                    .getDayCount());
        }

        for (RoomSchedule roomSchedule : schedule.schedules.values()) {

            List<Course> courses = roomSchedule.getCourses();
            coursesNotScheduled.removeAll(courses);
        }

        assertEquals(0, coursesNotScheduled.size());

    }

}
