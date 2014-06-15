package soa.common.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MethodNotAllowedInterceptor extends HandlerInterceptorAdapter {

	private Map<String, Set<String>> interceptedUrls = new HashMap<String, Set<String>>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		for (String url : interceptedUrls.keySet()) {
			if (request.getRequestURI().matches("(.*)" + url + "/?$")) {
				if (interceptedUrls.get(url).contains(request.getMethod())) {
					response.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
					return false;
				}
			}
		}
		return true;
	}

	public void addInterceptedUrl(String interceptedUrl, String... methods) {
		interceptedUrls.put(interceptedUrl,
				new HashSet<String>(Arrays.asList(methods)));
	}
}
