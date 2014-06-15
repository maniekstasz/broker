package soa.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import soa.common.model.AbstractUser;

public class Auditor<U extends AbstractUser> implements
		AuditorAware<AbstractUser> {

	@Autowired
	private CrudRepository<U, Long> userRepository;

	@Override
	public AbstractUser getCurrentAuditor() {
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
