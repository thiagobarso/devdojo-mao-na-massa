package br.com.devdojo.examgenerator.util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TokenUtil implements Serializable {

	private static final long serialVersionUID = -6797512999450818610L;

	public String getTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null)
			return "";
		List<Cookie> cookieList = Arrays.asList(request.getCookies());
		return getCookieByKey(cookieList, "token");
	}

	public boolean isExpirationTimeFromCookieValid(HttpServletRequest request) {
		if (request.getCookies() == null)
			return false;
		List<Cookie> cookiesList = Arrays.asList(request.getCookies());
		String expirationTime = getCookieByKey(cookiesList, "expirationTime");
		return validateIfTimeNowIsBeforeTokenExpires(expirationTime);
	}

	private String getCookieByKey(List<Cookie> cookiesList, String key) {
		String expirationTime = cookiesList.stream().filter(cookie -> cookie.getName().equals(key))
				.map(Cookie::getValue).findFirst().orElse("");
		return expirationTime;
	}

	private boolean validateIfTimeNowIsBeforeTokenExpires(String expirationTime) {
		if (expirationTime.isEmpty())
			return false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
				.withZone(ZoneId.of("UTC"));
		LocalDateTime tokenExpirationTime = LocalDateTime.parse(expirationTime, formatter);
		return LocalDateTime.now(ZoneId.of("UTC")).isBefore(tokenExpirationTime);
	}

}
