package org.ms.timepro.manager.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class JwtUserPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idUsuario;

	@JsonIgnore
	private String dscEmail;
	private String fullName;
	private String username;
	private String password;
	private String rut;
	private String userInput;
	private String mfaKey;
	private Boolean isBlocked;
	private Boolean isActive;
	private Boolean isMfa;
	private Collection<? extends GrantedAuthority> authorities;
	private List<JwtProfile> listaPerfiles;
    
    @Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
