package br.com.devdojo.examgenerator.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Course extends AbstractEntity {

	private static final long serialVersionUID = 2207856742928234451L;

	@NotEmpty(message = "the field name cannot be empty")
	@ApiModelProperty(notes = "The name of the course")
	private String name;

	@ManyToOne(optional = false)
	private Professor professor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public static final class Builder {
		private Course course;

		private Builder() {
			course = new Course();
		}

		public static Builder newCourse() {
			return new Builder();
		}

		public Builder id(Long id) {
			course.setId(id);
			return this;
		}

		public Builder name(String name) {
			course.setName(name);
			return this;
		}

		public Builder professor(Professor professor) {
			course.setProfessor(professor);
			return this;
		}

		public Course build() {
			return course;
		}
	}

}
