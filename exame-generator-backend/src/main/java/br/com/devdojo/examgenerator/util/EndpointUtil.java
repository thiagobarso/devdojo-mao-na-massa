package br.com.devdojo.examgenerator.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.devdojo.examgenerator.exception.ResourceNotFoundException;
import br.com.devdojo.examgenerator.persistence.model.ApplicationUser;
import br.com.devdojo.examgenerator.persistence.model.Professor;

@Service
public class EndpointUtil implements Serializable {

	private static final long serialVersionUID = -2773009728850932117L;

	public ResponseEntity<?> returnObjectOrNotFound(Object object) {
		if (object == null)
			throw new ResourceNotFoundException("Not Found");
		return new ResponseEntity<>(object, HttpStatus.OK);
	}

	public ResponseEntity<?> returnObjectOrNotFound(List<?> list) {
		if (list == null || list.isEmpty())
			throw new ResourceNotFoundException("Not Found");
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	public Professor extractProfessorFromToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((ApplicationUser) authentication.getPrincipal()).getProfessor();
	}

}
