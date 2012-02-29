package berlin.reiche.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;


public class MongoDBTest {

	@Test
	public void setUp() {
		assertNotSame(null, MongoDB.getDatastore());
		assertTrue(MongoDB.isConnected());
	}
}
