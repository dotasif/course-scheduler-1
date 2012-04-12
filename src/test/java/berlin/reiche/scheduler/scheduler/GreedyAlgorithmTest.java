package berlin.reiche.scheduler.scheduler;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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

		GreedyAlgorithm algorithm = new GreedyAlgorithm();
		List<Course> coursesNotScheduled = new ArrayList<>();
		for (CourseModule module : modules) {
			coursesNotScheduled.addAll(module.getCourses());
		}

		assertEquals(2, coursesNotScheduled.size());
		CourseSchedule schedule = algorithm.schedule(timeframe, modules, rooms);
		assertEquals(timeframe, schedule.timeframe);

		for (Entry<Room, List<List<Course>>> entry : schedule.schedule
				.entrySet()) {

			for (List<Course> courses : entry.getValue()) {
				coursesNotScheduled.removeAll(courses);
			}
		}
		
		assertEquals(0, coursesNotScheduled);

	}

}
