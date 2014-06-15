package soa.common.security;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import soa.common.model.AbstractUser;

public class SecurityUserRepository<U extends AbstractUser> implements
		UserDetailsService {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		U user;
		try {
			user = (U) em
					.createQuery(
							"select user from User user where user.credentials.username =:login or user.credentials.mail =:login")
					.setParameter("login", username).getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Username not found");
		}
		if (user == null)
			throw new UsernameNotFoundException("Username not found");
		return new LoggedUser(user.getId(),
				user.getCredentials().getUsername(), user.getCredentials()
						.getPassword(), new ArrayList<GrantedAuthority>(0));
	}

}
