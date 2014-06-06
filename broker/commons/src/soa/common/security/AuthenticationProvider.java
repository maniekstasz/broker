package soa.common.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import soa.common.model.User;
import soa.common.repository.UserRepository;

public class AuthenticationProvider implements
		org.springframework.security.authentication.AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String login = (String) authentication.getPrincipal();
		User user = userRepository
				.findByCredentialsUsernameOrCredentialsMail(login);
		if(user == null){
			throw new BadCredentialsException("Bad credentials");
		}
		LoggedUser loggedUser = new LoggedUser(user.getId(), user
				.getCredentials().getUsername(), user.getCredentials()
				.getPassword(), new ArrayList<GrantedAuthority>(0));

		authenticationChecks(loggedUser, authentication);

		if (loggedUser instanceof CredentialsContainer) {
			loggedUser.eraseCredentials();
		}

		return new UsernamePasswordAuthenticationToken(loggedUser,
				loggedUser.getPassword(), loggedUser.getAuthorities());
	}

	private void authenticationChecks(LoggedUser user,
			Authentication authentication) throws AuthenticationException {
		String requestPassword = (String) authentication.getCredentials();
		if (!passwordEncoder.matches(requestPassword, user.getPassword())) {
			throw new BadCredentialsException("Bad credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication);
	}

}
