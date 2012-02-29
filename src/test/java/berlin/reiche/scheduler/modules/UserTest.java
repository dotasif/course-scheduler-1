package berlin.reiche.scheduler.modules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {

	
	private static final String USER_NAME = "Charles";
	private static final String USER_PASSWORD = "puwafu59";
	private static final String WRONG_PASSWORD = "puwafu50";
	private static final String EMAIL = "charles@babbage.uk";

	@Test
	public void testPasswordHashing() {
		User user = new User(USER_NAME, USER_PASSWORD, EMAIL, true, false);
		assertNotSame(USER_PASSWORD, user.password);
		assertTrue(user.checkPassword(USER_PASSWORD));
		assertFalse(user.checkPassword(WRONG_PASSWORD));
	}
}
