package berlin.reiche.virginia;

import java.io.IOException;

import org.junit.Test;

import berlin.reiche.virginia.Main;

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

        try {
            assertNull(Main.server);
            Main.main();
            assertTrue(Main.server.isRunning());
        } catch (IOException e) {
            fail();
        }
    }
}
