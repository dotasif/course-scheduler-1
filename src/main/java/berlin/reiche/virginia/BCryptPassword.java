package berlin.reiche.virginia;

import org.eclipse.jetty.util.security.Credential;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Represents a password encoded with the {@link BCrypt} hashing in order to be
 * used with JAAS.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class BCryptPassword extends Credential {

    private final String password;

    /**
     * Default constructor.
     * 
     * @param password
     *            the hashed password string.
     */
    public BCryptPassword(String password) {
        this.password = password;
    }

    /**
     * @see org.eclipse.jetty.util.security.Credential#check(java.lang.Object)
     */
    @Override
    public boolean check(Object credentials) {

        if (credentials instanceof String) {
            return BCrypt.checkpw((String) credentials, password);
        }
        return false;
    }

}
