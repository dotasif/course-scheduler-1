package berlin.reiche.virginia.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import berlin.reiche.virginia.MongoDB;
import berlin.reiche.virginia.model.User;

public class UserTest {

    private static final String LOGIN = "Charles";
    private static final String NAME = "Charles Babbage";
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
        User user = new User(LOGIN, USER_PASSWORD, NAME, USER_EMAIL,
                IS_STUDENT, IS_LECTURER);
        assertNotSame(USER_PASSWORD, user.password);
        assertTrue(user.checkPassword(USER_PASSWORD));
        assertFalse(user.checkPassword(WRONG_PASSWORD));
    }
}
