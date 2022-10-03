package br.com.devdojo.examgenerator.persistence.model.support;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors {

	private String rejectedValue;

	private String field;

	private String defaultMessage;

	private List<Arguments> arguments;

	private String ObjectName;

	private List<String> codes;

	private String code;

	public String getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public List<Arguments> getArguments() {
		return arguments;
	}

	public void setArguments(List<Arguments> arguments) {
		this.arguments = arguments;
	}

	public String getObjectName() {
		return ObjectName;
	}

	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

}
