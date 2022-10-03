package br.com.devdojo.examgenerator.persistence.model.support;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token implements Serializable{
	
	private static final long serialVersionUID = -6999198902970742503L;
	
	private String token;
	@JsonProperty("exp")
	private LocalDateTime expirationTime;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}
	
	

}
