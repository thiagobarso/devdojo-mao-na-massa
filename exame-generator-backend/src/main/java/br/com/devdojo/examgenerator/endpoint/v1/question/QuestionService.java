package br.com.devdojo.examgenerator.endpoint.v1.question;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devdojo.examgenerator.exception.ResourceNotFoundException;
import br.com.devdojo.examgenerator.persistence.model.Question;
import br.com.devdojo.examgenerator.persistence.repository.QuestionRepository;

@Service
public class QuestionService implements Serializable {

	private static final long serialVersionUID = -4896545715887942916L;
	
	private final QuestionRepository questionRepository;

	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		super();
		this.questionRepository = questionRepository;
	}

	public void throwResourceNotFoundIfQuestionDowsNotExist(Question question) {
		if (question == null || question.getId() == null || questionRepository.findOne(question.getId()) == null)
			throw new ResourceNotFoundException("Question not Found");
	}
	
	public void throwResourceNotFoundIfQuestionDowsNotExist(long questionId) {
		if (questionId == 0 || questionRepository.findOne(questionId) == null)
			throw new ResourceNotFoundException("Question not Found");
	}

}
