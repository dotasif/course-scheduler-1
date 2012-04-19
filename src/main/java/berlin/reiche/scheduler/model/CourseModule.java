package berlin.reiche.scheduler.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

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
    String assessmentType;

    /**
     * List of courses assigned to the module
     */
    @Embedded
    List<Course> courses;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private CourseModule() {

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
    public CourseModule(String name, int credits, String assessmentType) {
        super();
        this.name = name;
        this.credits = credits;
        this.assessmentType = assessmentType;
        this.courses = new ArrayList<>();
    }

    public String getAssessmentType() {
        return assessmentType;
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

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
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
