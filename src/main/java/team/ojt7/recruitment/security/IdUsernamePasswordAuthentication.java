package team.ojt7.recruitment.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class IdUsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
	
	private Object id;
	
	public IdUsernamePasswordAuthentication(Object id, Object principal, Object credentials) {
		super(principal, credentials);
		this.id = id;
	}
	
	public IdUsernamePasswordAuthentication(Object id, Object principal, Object credentials, List<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.id = id;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

}
