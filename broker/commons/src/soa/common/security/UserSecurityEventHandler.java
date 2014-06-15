package soa.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import soa.common.model.AbstractUser;

@RepositoryEventHandler(AbstractUser.class)
public class UserSecurityEventHandler {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@HandleBeforeCreate
	@HandleBeforeSave
	public void handleBeforeCreate(AbstractUser user) {
		String password = user.getCredentials().getPassword();
		String encodedPassword = encoder.encode(password);
		user.getCredentials().setPassword(encodedPassword);
	}
}
