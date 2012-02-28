package berlin.reiche.scheduler;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppServletTest {

	AppServlet servlet;

	@Before
	public void setUp() {
		assertNotSame(null, AppServlet.getInstance());
	}

	@Test
	public void testProcessTemplate() {

	}

}
