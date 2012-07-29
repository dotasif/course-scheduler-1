package berlin.reiche.virginia.scheduler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.model.User;
import berlin.reiche.virginia.scheduler.Scheduler;

public class SchedulerTest {

    List<CourseModule> modules = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    List<User> lecturers = new ArrayList<>();
    Timeframe timeframe;
    CourseModule module;
    Room room;
    
    InputData data;

    static List<String> weekdays = new ArrayList<>();

    @BeforeClass
    public static void setUpBeforeClass() {
        weekdays.add("Monday");
        weekdays.add("Tuesday");
        weekdays.add("Wednesday");
        weekdays.add("Thursday");
        weekdays.add("Friday");
    }

    @Before
    public void setUp() {
        timeframe = new Timeframe(5, 12, 8, weekdays);
        room = new Room("SR 005", "Seminar Room");
        module = new CourseModule("Advanced Algorithms", 8, "Exam");
        module.getCourses().add(new Course("Lecture", 2, 2));
        module.getCourses().add(new Course("Tutorial", 1, 2));
        lecturers.add(new User("charles", "puwafu59", "charles@babbage.uk",
                false, true));
        
        data = new InputData();
        data.timeframe = timeframe;
        data.rooms = rooms;
        data.modules = modules;
        data.lecturers = lecturers;
    }

    @Test
    public void testIsScheduleable() {

        Scheduler scheduler = new Scheduler();
        Feedback feedback = new Feedback();
        
        assertFalse(scheduler.isSchedulable(data, feedback));

        rooms.add(room);
        assertTrue(scheduler.isSchedulable(data, feedback));

        modules.add(module);
        assertTrue(scheduler.isSchedulable(data, feedback));
    }

}
