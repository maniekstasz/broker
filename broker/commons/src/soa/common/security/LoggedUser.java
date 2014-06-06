package soa.common.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoggedUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 326479337951276717L;

	private Long id;

	public LoggedUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
	}

	public LoggedUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
