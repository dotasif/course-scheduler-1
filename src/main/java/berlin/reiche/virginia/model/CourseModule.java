package berlin.reiche.virginia.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

/**
 * A course module is a self-contained, formally structured learning experience.
 * A course module has one or more courses which have to be undertaken in oder
 * to complete this module.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("course_module")
public class CourseModule {

    @Id
    ObjectId id;
    String name;
    int credits;
    String assessment;

    /**
     * List of courses assigned to the module
     */
    @Reference
    List<Course> courses;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private CourseModule() {

    }

    /**
     * Null object for form processing purposes.
     */
    public static final CourseModule NULL_MODULE;

    static {
        NULL_MODULE = new CourseModule(null, -1, null);
        NULL_MODULE.getCourses().add(Course.NULL_COURSE);
    }

    /**
     * Creates a new course module by assigning the parameters directly, except
     * the id which is generated before saving the object to the database.
     * 
     * @param name
     *            the name.
     * @param credits
     *            the credit points.
     * @param assessmentType
     *            the assessment type.
     */
    public CourseModule(String name, int credits, String assessment) {
        super();
        this.name = name;
        this.credits = credits;
        this.assessment = assessment;
        this.courses = new ArrayList<>();
    }

    public String getAssessment() {
        return assessment;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public int getCredits() {
        return credits;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public void setAssessment(String assessmentType) {
        this.assessment = assessmentType;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
