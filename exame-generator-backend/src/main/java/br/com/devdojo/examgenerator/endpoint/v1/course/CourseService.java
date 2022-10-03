package br.com.devdojo.examgenerator.endpoint.v1.course;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devdojo.examgenerator.exception.ResourceNotFoundException;
import br.com.devdojo.examgenerator.persistence.model.Course;
import br.com.devdojo.examgenerator.persistence.repository.CourseRepository;

@Service
public class CourseService implements Serializable {

	private static final long serialVersionUID = -6823305878364341403L;

	private final CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		super();
		this.courseRepository = courseRepository;
	}

	public void throwResourceNotFoundIfCourseDowsNotExist(Course course) {
		if (course == null || course.getId() == null || courseRepository.findOne(course.getId()) == null)
			throw new ResourceNotFoundException("Course not Found");
	}
	
	public void throwResourceNotFoundIfCourseDowsNotExist(long courseId) {
		if (courseId == 0 || courseRepository.findOne(courseId) == null)
			throw new ResourceNotFoundException("Course not Found");
	}

}
