package soa.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import soa.common.model.User;

@RepositoryEventHandler(User.class)
public class UserSecurityEventHandler {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@HandleBeforeCreate
	public void handleBeforeCreate(User user) {
		String password = user.getCredentials().getPassword();
		String encodedPassword = encoder.encode(password);
		user.getCredentials().setPassword(encodedPassword);
	}
}
