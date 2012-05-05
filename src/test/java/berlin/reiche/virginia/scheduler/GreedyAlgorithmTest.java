package berlin.reiche.virginia.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.scheduler.CourseSchedule;
import berlin.reiche.virginia.scheduler.GreedyAlgorithm;
import berlin.reiche.virginia.scheduler.RoomSchedule;
import berlin.reiche.virginia.scheduler.InputData;

public class GreedyAlgorithmTest {

    private static final String EXAM = "Exam";
    private static final String LECTURE = "Lecture";
    private static final String TUTORIAL = "Tutorial";
    private static final String SEMINAR_ROOM = "Seminar Room";

    List<CourseModule> modules = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();

    static List<String> weekdays = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() {
        weekdays.add("Monday");
        weekdays.add("Tuesday");
        weekdays.add("Wednesday");
        weekdays.add("Thursday");
        weekdays.add("Friday");
    }

    Timeframe timeframe = new Timeframe(5, 12, 8, weekdays);

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

        InputData data = new InputData();
        data.timeframe = timeframe;
        data.modules = modules;
        data.rooms = rooms;

        GreedyAlgorithm algorithm = new GreedyAlgorithm(data);

        List<Course> coursesNotScheduled = new ArrayList<>();
        for (CourseModule module : modules) {
            for (Course course : module.getCourses()) {
                for (int i = 0; i < course.getCount(); i++) {
                    coursesNotScheduled.add(course);
                }
            }
        }

        assertEquals(3, coursesNotScheduled.size());
        CourseSchedule schedule = algorithm.schedule(data);
        assertEquals(timeframe, schedule.timeframe);

        for (Room room : rooms) {
            assertNotNull(schedule.getSchedules().get(room));
            assertEquals(timeframe.getDays(), schedule.getSchedules().get(room)
                    .getDayCount());
        }

        for (RoomSchedule roomSchedule : schedule.getSchedules().values()) {

            List<Course> courses = roomSchedule.getCourses();
            coursesNotScheduled.removeAll(courses);
        }

        assertEquals(0, coursesNotScheduled.size());

    }

}
