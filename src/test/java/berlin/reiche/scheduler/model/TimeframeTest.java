package berlin.reiche.scheduler.model;

import org.junit.Test;

public class TimeframeTest {

	@Test
	public void testConstructor1() {
		new Timeframe(5, 12);
	}

	@Test(expected = IllegalStateException.class)
	public void testConstructor2() {
		new Timeframe(0, 12);
	}

	@Test(expected = IllegalStateException.class)
	public void testConstructor3() {
		new Timeframe(5, 0);
	}

	@Test(expected = IllegalStateException.class)
	public void testConstructor4() {
		new Timeframe(-1, -1);
	}

}
