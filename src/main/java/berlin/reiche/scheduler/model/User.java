package berlin.reiche.scheduler.model;

import org.mindrot.jbcrypt.BCrypt;

import berlin.reiche.scheduler.MongoDB;

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
	User(String name, String password, String email, boolean isStudent,
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
	 * Stores a new user in the database.
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
	 * @return the id of the saved user, the name if successful, null if not.
	 */
	public static String saveUser(String name, String password, String email,
			boolean isStudent, boolean isLecturer) {
		User newUser = new User(name, password, email, isStudent, isLecturer);
		return MongoDB.getDatastore().save(newUser).getId().toString();
	}

	/**
	 * Deletes a user with the given login name.
	 * 
	 * @param name
	 *            the login name.
	 */
	public static void deleteUser(String name) {
		MongoDB.getDatastore().delete(User.class, name);
	}

	/**
	 * Retrieves a user from the database through its login name.
	 * 
	 * @param id
	 *            the login name.
	 * @return the retrieved user.
	 */
	public static User getUser(String id) {
		return MongoDB.getDatastore().get(User.class, id);
	}

}
