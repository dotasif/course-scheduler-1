package berlin.reiche.scheduler.modules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import berlin.reiche.scheduler.MongoDB;

public class UserTest {

	private static final String USER_NAME = "Charles";
	private static final String USER_PASSWORD = "puwafu59";
	private static final String WRONG_PASSWORD = "puwafu50";
	private static final String USER_EMAIL = "charles@babbage.uk";
	private static final boolean IS_STUDENT = true;
	private static final boolean IS_LECTURER = false;

	@Before
	public void setUp() {
		assertTrue(MongoDB.isConnected());
	}

	@Test
	public void testPasswordHashing() {
		User user = new User(USER_NAME, USER_PASSWORD, USER_EMAIL, IS_STUDENT,
				IS_LECTURER);
		assertNotSame(USER_PASSWORD, user.password);
		assertTrue(user.checkPassword(USER_PASSWORD));
		assertFalse(user.checkPassword(WRONG_PASSWORD));
	}

	@Test
	public void testSaveUser() {
		User user = MongoDB.getDatastore().get(User.class, USER_NAME);
		assertNull(user);
		
		String id = User.saveUser(USER_NAME, USER_PASSWORD, USER_EMAIL,
				IS_STUDENT, IS_LECTURER);
		assertEquals(USER_NAME, id);
		
		user = MongoDB.getDatastore().get(User.class, USER_NAME);
		assertEquals(USER_NAME, user.name);
		assertTrue(user.checkPassword(USER_PASSWORD));
		assertFalse(user.checkPassword(WRONG_PASSWORD));
		assertEquals(USER_EMAIL, user.email);
		assertEquals(IS_STUDENT, user.isStudent);
		assertEquals(IS_LECTURER, user.isLecturer);
		
		User.deleteUser(USER_NAME);
		user = MongoDB.getDatastore().get(User.class, id);
		assertNull(user);
	}
}
