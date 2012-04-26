package berlin.reiche.virginia.model;

import org.mindrot.jbcrypt.BCrypt;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * A User is someone who is using the course scheduler and represent different
 * roles.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("user")
public class User {

    /**
     * The log2 of the number of rounds of hashing to apply. The work factor
     * there increases as 2**log_rounds.
     */
    private static final int LOG_ROUNDS = 12;

    @Id
    String name;
    String password;
    String email;
    boolean isStudent;
    boolean isLecturer;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private User() {

    }

    /**
     * Creates a new user by assigning the parameters directly, except the
     * password which is hashed.
     * 
     * @param name
     *            the login name.
     * @param password
     *            the login password.
     * @param email
     *            the email address.
     * @param isStudent
     *            whether the user is a student.
     * @param isLecturer
     *            whether the user is a lecturer.
     */
    public User(String name, String password, String email, boolean isStudent,
            boolean isLecturer) {
        super();
        this.name = name;
        this.password = hashPassword(password);
        this.email = email;
        this.isStudent = isStudent;
        this.isLecturer = isLecturer;
    }

    /**
     * Applies hashing to a given String.
     * 
     * @param password
     *            the password to hash.
     * @return the hashed password.
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    /**
     * Checks whether a given password String is the correct password for this
     * user.
     * 
     * @param candidate
     *            the candidate password to verify.
     * @return whether the candidate password is the actual password.
     */
    public boolean checkPassword(String candidate) {
        return BCrypt.checkpw(candidate, password);
    }

    /**
     * Replaces the old password with a new password only if the correct old
     * password is provided.
     * 
     * @param oldPassword
     *            the current password.
     * @param newPassword
     *            the new password.
     */
    public void changePassword(String oldPassword, String newPassword) {

        if (checkPassword(oldPassword)) {
            password = hashPassword(newPassword);
        } else {
            throw new IllegalStateException("The old password is wrong");
        }
    }

}
