package berlin.reiche.scheduler.model;

import java.util.Map;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

/**
 * Represents a course unit which belongs to a {@link CourseModule}.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("course")
public class Course {

	@Id
	long id;
	int duration;
	int count;
	String type;
	
	@Reference
	Map<String, Integer> features;
}
