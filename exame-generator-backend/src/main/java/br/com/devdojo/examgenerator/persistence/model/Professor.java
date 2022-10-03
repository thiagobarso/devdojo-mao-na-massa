package br.com.devdojo.examgenerator.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Professor extends AbstractEntity {

	private static final long serialVersionUID = -6809215273471041306L;

	@NotEmpty(message = "The field name cannot be empty")
	private String name;

	@Email(message = "This e-mail is not valid")
	@NotEmpty(message = "The field e-mail cannot be empty")
	@Column(unique = true)
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static final class Builder {
		private Professor professor;

		private Builder() {
			professor = new Professor();
		}

		public static Builder newProfessor() {
			return new Builder();
		}

		public Builder id(Long id) {
			professor.setId(id);
			return this;
		}

		public Builder name(String name) {
			professor.setName(name);
			return this;
		}
		
		public Builder email(String email) {
			professor.setName(email);
			return this;
		}

		public Professor build() {
			return professor;
		}
	}

}
