package br.com.devdojo.examgenerator.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.devdojo.examgenerator.persistence.model.Question;

public interface QuestionRepository extends CustomPagingAndSortRepository<Question, Long> {

	@Query("select q from Question q where q.course.id = ?1 and q.title like %?2% and q.professor = ?#{principal.professor} and q.enable = true")
	List<Question> listQuestionsByCourseAndTitle(long courseId, String title);

}
