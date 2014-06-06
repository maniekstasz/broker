package soa.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import soa.common.model.User;
import soa.common.repository.UserRepository;

public class Auditor implements AuditorAware<User> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			LoggedUser loggedUser = (LoggedUser) auth.getPrincipal();
			if (loggedUser == null)
				return null;
			return userRepository.findOne(loggedUser.getId());
		}
		return null;
	}

}
