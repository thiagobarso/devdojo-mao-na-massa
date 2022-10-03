package br.com.devdojo.examgenerator.endpoint.v1.question;

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
import br.com.devdojo.examgenerator.persistence.model.Question;
import br.com.devdojo.examgenerator.persistence.repository.QuestionRepository;
import br.com.devdojo.examgenerator.util.EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("v1/professor/course/question")
@Api(description = "Operations related to question's question")
public class QuestionEndpoint {

	private final QuestionRepository questionRepository;
	private final GenericService service;
	private final EndpointUtil endpointUtil;

	@Autowired
	public QuestionEndpoint(QuestionRepository questionRepository, GenericService service, EndpointUtil endpointUtil) {
		super();
		this.questionRepository = questionRepository;
		this.service = service;
		this.endpointUtil = endpointUtil;
	}

	@ApiOperation(value = "Return a question based on it's id", response = Question.class)
	@GetMapping(path = "{id}")
	private ResponseEntity<?> getQuestionById(@PathVariable long id) {
		return endpointUtil.returnObjectOrNotFound(questionRepository.findOne(id));
	}

	@ApiOperation(value = "Return a list of questions related to course", response = Question.class)
	@GetMapping(path = "list/{courseId}")
	private ResponseEntity<?> listQuestions(@PathVariable long courseId,
			@ApiParam("Question title") @RequestParam(value = "title", defaultValue = "") String name) {
		return new ResponseEntity<>(questionRepository.listQuestionsByCourseAndTitle(courseId, name), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a specific question and return 200 Ok with no body")
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		service.throwResourceNotFoundIfDoesNotExist(id, questionRepository, "Question not found");
		questionRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Update question and return 200 ok with no body")
	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Question question) {
		service.throwResourceNotFoundIfDoesNotExist(question, questionRepository, "Question not found");
		questionRepository.save(question);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Create question and return the question created", response = Question.class)
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Question question) {
        service.throwResourceNotFoundIfDoesNotExist(question, questionRepository, "Question not found");
        question.setProfessor(endpointUtil.extractProfessorFromToken());
        return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
    }

}
