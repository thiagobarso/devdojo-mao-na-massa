package br.com.devdojo.examgenerator.persistence.model;

public class Professor extends AbstractEntity{
	
	private static final long serialVersionUID = -6809215273471041306L;

//	@NotEmpty(message = "The field name cannot be empty")
	private String name;
	
//	@Email(message = "This e-mail is not valid")
//	@NotEmpty(message = "The field e-mail cannot be empty")
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
	
	

}
