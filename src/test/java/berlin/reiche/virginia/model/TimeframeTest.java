package berlin.reiche.virginia.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import berlin.reiche.virginia.model.Timeframe;

public class TimeframeTest {
	
	static List<String> weekdays = new ArrayList<>();
	
	@BeforeClass
	public static void setUp() {
		weekdays.add("Monday");
		weekdays.add("Tuesday");
		weekdays.add("Wednesday");
		weekdays.add("Thursday");
		weekdays.add("Friday");
	}
	
    @Test
    public void testConstructor1() {
		new Timeframe(5, 12, 8, weekdays);
    }

    @Test(expected = IllegalStateException.class)
    public void testConstructor2() {
        new Timeframe(0, 12, 8, weekdays);
    }

    @Test(expected = IllegalStateException.class)
    public void testConstructor3() {
        new Timeframe(5, 0, 8, weekdays);
    }

    @Test(expected = IllegalStateException.class)
    public void testConstructor4() {
        new Timeframe(-1, -1, 8, weekdays);
    }

}
