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
public class CourseEditBean implements Serializable {

	private static final long serialVersionUID = 1874900613331313308L;
	private final CourseDAO courseDAO;
	private long id;
	private Course course;

	@Inject
	public CourseEditBean(CourseDAO courseDAO) {
		super();
		this.courseDAO = courseDAO;
	}

	public void init() {
		course = courseDAO.findOne(id);
	}

	@ExceptionHandler
	public String update() {
		courseDAO.update(course);
		Messages.create("The course {0} was successfully updated.", course.getName()).flash().add();
		return "list.xhtml?faces-redirect=true";
	}
	
	@ExceptionHandler
	public String delete() {
		courseDAO.delete(course);
		Messages.create("The course {0} was successfully deleted.", course.getName()).flash().add();
		return "list.xhtml?faces-redirect=true";
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
