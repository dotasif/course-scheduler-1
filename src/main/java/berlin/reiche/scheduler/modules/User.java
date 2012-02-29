package berlin.reiche.scheduler.modules;

import org.mindrot.jbcrypt.BCrypt;

/**
 * A User is someone who is using the course scheduler and represent different
 * roles.
 * 
 * @author Konrad Reiche
 * 
 */
public class User {

	/**
	 * The log2 of the number of rounds of hashing to apply. The work factor
	 * there increases as 2**log_rounds.
	 */
	private static final int LOG_ROUNDS = 12;

	String name;
	String password;
	String email;
	boolean isStudent;
	boolean isLecturer;

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

}
