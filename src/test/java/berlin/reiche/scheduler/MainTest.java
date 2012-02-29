package berlin.reiche.scheduler;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Konrad Reiche
 * 
 */
public class MainTest {

	/**
	 * Tests whether the server is started correctly.
	 */
	@Test
	public void testMain() {
		
		assertNull(Main.server);
		Main.main();
		assertTrue(Main.server.isRunning());
	}
}
