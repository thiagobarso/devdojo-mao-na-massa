package br.com.devdojo.examgenerator.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = -2703065871945105164L;

	public CustomObjectMapper() {
		this.registerModule(new JavaTimeModule());
	}

}
