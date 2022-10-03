package br.com.devdojo.examgenerator.bean.course;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.persistence.dao.CourseDAO;
import br.com.devdojo.examgenerator.persistence.model.Course;

@Named
@ViewScoped
public class CourseRegisterBean implements Serializable {

	private static final long serialVersionUID = 53395454236695398L;

	private final CourseDAO courseDAO;

	private Course course = new Course();

	@Inject
	public CourseRegisterBean(CourseDAO courseDAO) {
		super();
		this.courseDAO = courseDAO;
	}

	@ExceptionHandler
	public String save() {
		Course course = courseDAO.create(this.course);
		Messages.create("The course {0} was successfully added.", course.getName()).flash().add();
		return "list.xhtml?faces-redirect=true";
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
