package soa.common.security;

import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;

public class SecurityEventHandlerSupportBean {

	public LoggedUser getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			LoggedUser user = (LoggedUser) auth.getPrincipal();
			if (user == null)
				throw new AccessDeniedException("Not authorized");
			return user;
		} else {
			throw new AccessDeniedException("Not authorized");
		}

	}

	public boolean isTheSameUser(LoggedUser loggedUser,
			HttpServletRequest request) {
		String parts[] = request.getRequestURI().split("/");
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].equals("users") && i + 1 < parts.length
					&& parts[i + 1].matches("[0-9]*")){
				return loggedUser.getId().equals(new Long(parts[i + 1]));
			}
		}
		return true;
	}

	public <T> HttpEntity<T> addAuthenticationHeader(T entity, String password,
			String username) {
		HttpHeaders requestHeaders = new HttpHeaders();
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
				.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		requestHeaders.add("Authorization", authHeader);
		HttpEntity<T> result = new HttpEntity<T>(entity, requestHeaders);

		return result;
	}

}
