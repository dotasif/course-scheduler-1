package berlin.reiche.virginia.scheduler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import berlin.reiche.virginia.model.Course;
import berlin.reiche.virginia.model.CourseModule;
import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.Timeframe;
import berlin.reiche.virginia.scheduler.Scheduler;

public class SchedulerTest {

	List<CourseModule> modules = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();
	Timeframe timeframe;
	
	CourseModule module;
	Room room;

	@Before
	public void setUp() {
		timeframe = new Timeframe(5, 12);
		room = new Room("SR 005", "Seminar Room");
		module = new CourseModule("Advanced Algorithms", 8, "Exam");
		module.getCourses().add(new Course("Lecture", 2, 2));
		module.getCourses().add(new Course("Tutorial", 1, 2));
	}

	@Test
	public void testIsScheduleable() {

		Scheduler scheduler = new Scheduler();
		assertFalse(scheduler.isScheduleable(modules, rooms, timeframe));
		
		rooms.add(room);
		assertTrue(scheduler.isScheduleable(modules, rooms, timeframe));
		
		modules.add(module);
		assertTrue(scheduler.isScheduleable(modules, rooms, timeframe));
	}

}
