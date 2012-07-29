package berlin.reiche.virginia.servlets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import berlin.reiche.virginia.servlets.AppServlet;

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
        assertNotNull(AppServlet.configuration);
        assertTrue(AppServlet.configuration.getObjectWrapper() instanceof DefaultObjectWrapper);
    }

}
