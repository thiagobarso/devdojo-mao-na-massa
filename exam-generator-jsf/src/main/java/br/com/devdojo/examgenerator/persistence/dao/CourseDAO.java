package br.com.devdojo.examgenerator.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.custom.CustomRestTemplate;
import br.com.devdojo.examgenerator.custom.CustomTypeReference;
import br.com.devdojo.examgenerator.persistence.model.Course;
import br.com.devdojo.examgenerator.util.ApiUtil;
import br.com.devdojo.examgenerator.util.JsonUtil;

public class CourseDAO implements Serializable {

	private static final long serialVersionUID = -4588269653030246814L;
	private final String LIST_URL = ApiUtil.BASE_URL + "/professor/course/list";
	private final String DELETE_OR_FIND_ONE_URL = ApiUtil.BASE_URL + "/professor/course/{id}";
	private final String CREATE_UPDATE_URL = ApiUtil.BASE_URL + "/professor/course/";
	private final CustomRestTemplate restTemplate;
	private final JsonUtil jsonUtil;
	private final CustomTypeReference<List<Course>> listCourse;

	@Inject
	public CourseDAO(CustomRestTemplate restTemplate, JsonUtil jsonUtil,
			CustomTypeReference<List<Course>> listCourseTypeReference) {
		super();
		this.restTemplate = restTemplate;
		this.jsonUtil = jsonUtil;
		this.listCourse = listCourseTypeReference;
	}

	@ExceptionHandler
	public List<Course> list(String name) {
		UriComponents url = UriComponentsBuilder.fromUriString(LIST_URL).queryParam("name", name).build();
		ResponseEntity<List<Course>> exchange = restTemplate.exchange(url.toString(), HttpMethod.GET,
				jsonUtil.tokenizedHttpEntityHeader(), listCourse.typeReference());
		return exchange.getBody();

	}

	@ExceptionHandler
	public Course findOne(long id) {
		return restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.GET, jsonUtil.tokenizedHttpEntityHeader(),
				Course.class, id).getBody();
	}

	public Course update(Course course) {
		return createOrUpdate(HttpMethod.PUT, course);
	}

	public Course create(Course course) {
		return createOrUpdate(HttpMethod.POST, course);

	}

	private Course createOrUpdate(HttpMethod httpMethod, Course course) {
		return restTemplate
				.exchange(CREATE_UPDATE_URL, httpMethod, jsonUtil.tokenizedHttpEntityHeader(course), Course.class)
				.getBody();
	}

	public void delete(Course course) {
		restTemplate.exchange(DELETE_OR_FIND_ONE_URL, HttpMethod.DELETE, jsonUtil.tokenizedHttpEntityHeader(course),
				Course.class, course.getId());

	}
}
