package berlin.reiche.virginia.model;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

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

    /**
     * The login name credentials.
     */
    @Id
    String login;

    /**
     * The password credentials.
     */
    String password;

    /**
     * The real name.
     */
    String name;

    /**
     * The email address.
     */
    String email;

    /**
     * Whether the user is a student.
     */
    boolean student;

    /**
     * Whether the user is a lecturer.
     */
    boolean lecturer;

    /**
     * A list of course which the user can lecture.
     */
    @Reference
    List<Course> responsibleCourses;

    /**
     * The roles being used for the authentication.
     */
    public static final String[] ROLES = new String[] { "user" };

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
     * @param name
     *            the real name.
     * @param email
     *            the email address.
     * @param student
     *            whether the user is a student.
     * @param lecturer
     *            whether the user is a lecturer.
     */
    public User(String login, String password, String name, String email,
            boolean student, boolean lecturer) {
        super();
        this.login = login;
        this.password = hashPassword(password);
        this.name = name;
        this.email = email;
        this.student = student;
        this.lecturer = lecturer;
        this.responsibleCourses = new ArrayList<>();
    }

    /**
     * Adds a course to the responsible courses the user can lecture.
     * 
     * @param course
     *            the course.
     */
    public void addCourse(Course course) {
        responsibleCourses.add(course);
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

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Course> getResponsibleCourses() {
        return responsibleCourses;
    }

    public boolean hasCourse(Course course) {
        return true;
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

    public boolean isLecturer() {
        return lecturer;
    }

    public boolean isStudent() {
        return student;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLecturer(boolean lecturer) {
        this.lecturer = lecturer;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }
    
    

}
