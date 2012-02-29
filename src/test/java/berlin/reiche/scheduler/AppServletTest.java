package berlin.reiche.scheduler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import freemarker.template.DefaultObjectWrapper;

/**
 * Tests the initialization of the servlet. 
 * 
 * @author Konrad Reiche
 *
 */
public class AppServletTest {

	AppServlet servlet;

	@Test
	public void setUp() {
		servlet = AppServlet.getInstance();
		assertNotNull(servlet);
		assertNotNull(servlet.configuration);
		assertTrue(servlet.configuration.getObjectWrapper() instanceof DefaultObjectWrapper);
	}

}
