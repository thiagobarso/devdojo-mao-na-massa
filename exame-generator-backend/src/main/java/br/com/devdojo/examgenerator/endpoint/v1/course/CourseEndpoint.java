package br.com.devdojo.examgenerator.endpoint.v1.course;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.devdojo.examgenerator.endpoint.v1.genericservice.GenericService;
import br.com.devdojo.examgenerator.persistence.model.Course;
import br.com.devdojo.examgenerator.persistence.repository.CourseRepository;
import br.com.devdojo.examgenerator.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("v1/professor/course")
@Api(description = "Operations related to professor's course")
public class CourseEndpoint {

	private final CourseRepository courseRepository;
	private final GenericService service;
	private final EndpointUtil endpointUtil;

	@Autowired
	public CourseEndpoint(CourseRepository courseRepository, GenericService service, EndpointUtil endpointUtil) {
		super();
		this.courseRepository = courseRepository;
		this.service = service;
		this.endpointUtil = endpointUtil;
	}

	@ApiOperation(value = "Return a course based on it's id", response = Course.class)
	@GetMapping(path = "{id}")
	private ResponseEntity<?> getCourseById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(courseRepository.findOne(id));
	}

	@ApiOperation(value = "Return a list of courses related to professor", response = Course.class)
	@GetMapping(path = "list")
	private ResponseEntity<?> listCourses(
			@ApiParam("Course Name") @RequestParam(value = "name", defaultValue = "") String name) {
		return new ResponseEntity<>(courseRepository.listCoursesByName(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a specific course and return 200 Ok with no body")
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		service.throwResourceNotFoundIfDoesNotExist(id, courseRepository, "Course not found");
		courseRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update course and return 200 ok with no body")
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Course course) {
		service.throwResourceNotFoundIfDoesNotExist(course, courseRepository, "Course not found");
		courseRepository.save(course);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Create course and return the course created")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Course course) {
		course.setProfessor(endpointUtil.extractProfessorFromToken());
		return new ResponseEntity<>(courseRepository.save(course),HttpStatus.OK);
	}


}
