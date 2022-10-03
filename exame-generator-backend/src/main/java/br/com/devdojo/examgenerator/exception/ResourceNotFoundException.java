package br.com.devdojo.examgenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4553695431103667069L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
