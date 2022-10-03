package br.com.devdojo.examgenerator.endpoint.v1.course;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devdojo.examgenerator.endpoint.v1.ProfessorEndpointTest;
import br.com.devdojo.examgenerator.persistence.model.Course;
import br.com.devdojo.examgenerator.persistence.repository.CourseRepository;
import br.com.devdojo.examgenerator.persistence.repository.ProfessorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CourseEndpointTest {

	@MockBean
	private CourseRepository courseRepository;

	@MockBean
	private ProfessorRepository professorRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private HttpEntity<Void> professorHeader;

	private HttpEntity<Void> wrongHeader;

	private Course course = mockCourse();

	public static Course mockCourse() {
		return Course.Builder.newCourse().id(new Long(1L)).name("Java").professor(ProfessorEndpointTest.mockProfessor()).build();
	}

	@Before
	public void configProfessorHeader() {
		String body = "{\"username\":\"william\",\"password\":\"devdojo\"}";
		HttpHeaders headers = testRestTemplate.postForEntity("/login", body, String.class).getHeaders();
		this.professorHeader = new HttpEntity<>(headers);
	}

	@Before
	public void configWrongHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "1111");
		this.wrongHeader = new HttpEntity<>(headers);
	}

	@Before
	public void setup() {
		BDDMockito.when(courseRepository.findOne(course.getId())).thenReturn(course);
		BDDMockito.when(courseRepository.listCoursesByName("")).thenReturn(Collections.singletonList(course));
		BDDMockito.when(courseRepository.listCoursesByName("java")).thenReturn(Collections.singletonList(course));
	}

	@Test
	public void getCourseByIdWhenTokenIsWrongShouldReturn403() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/1", HttpMethod.GET,
				wrongHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void listCourseWhenTokenIsWrongShouldReturn403() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/list?name=", HttpMethod.GET,
				wrongHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
	}

	@Test
	public void listAllCoursesWhenNameDoesNotExistsShouldReturnEmptyList() throws Exception {
		ResponseEntity<List<Course>> exchange = testRestTemplate.exchange("/v1/professor/course/list?name=caca",
				HttpMethod.GET, professorHeader, new ParameterizedTypeReference<List<Course>>() {
				});
		assertThat(exchange.getBody()).isEmpty();
	}

	@Test
	public void listAllCoursesWhenNameExistsShouldReturn200() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/list?name=java",
				HttpMethod.GET, professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getCourseByIdWithoutIdShouldReturn400() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/", HttpMethod.GET,
				professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void getCourseByIdWhenCourseIdDoesNotExistsShouldReturn404() throws Exception {
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/-1", HttpMethod.GET,
				professorHeader, String.class);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void getCourseByIdWhenCourseExistsShouldReturn200() throws Exception {
		long id = 1L;
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/{id}", HttpMethod.GET,
				professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteCourseWhenIdExistsShouldReturn200() throws Exception {
		long id = 1L;
		BDDMockito.doNothing().when(courseRepository).delete(id);
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/{id}", HttpMethod.DELETE,
				professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteCourseWhenIdDoesNotExistsShouldReturn404() throws Exception {
		long id = -1L;
		BDDMockito.doNothing().when(courseRepository).delete(id);
		ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/professor/course/{id}", HttpMethod.GET,
				professorHeader, String.class, id);
		assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void createCourseWhenNameIsNullShouldReturn400() throws Exception {
		Course course = courseRepository.findOne(1L);
		course.setName(null);
		assertThat(createCourse(course).getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	public void createCourseWhenEverythingIsRightShouldReturn200() throws Exception {
		Course course = courseRepository.findOne(1L);
		course.setId(null);
		assertThat(createCourse(course).getStatusCodeValue()).isEqualTo(200);
	}

	private ResponseEntity<String> createCourse(Course course) {
		BDDMockito.when(courseRepository.save(course)).thenReturn(course);
		return testRestTemplate.exchange("/v1/professor/course", HttpMethod.POST,
				new HttpEntity<>(course, professorHeader.getHeaders()), String.class);
	}

}
