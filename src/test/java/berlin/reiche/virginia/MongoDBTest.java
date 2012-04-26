package berlin.reiche.virginia;

import static org.junit.Assert.*;

import org.junit.Test;

import berlin.reiche.virginia.MongoDB;

public class MongoDBTest {

    @Test
    public void setUp() {
        assertNotSame(null, MongoDB.getDatastore());
        assertTrue(MongoDB.isConnected());
    }
}
