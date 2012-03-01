package berlin.reiche.scheduler.model;

import java.util.Map;

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
	int id;
	String name;
	int credits;
	String assessmentType;

	/**
	 * List of courses assigned to the module each mapped to the number how
	 * often the same course is offered. 
	 */
	@Embedded
	Map<Course, Integer> courses;
}
