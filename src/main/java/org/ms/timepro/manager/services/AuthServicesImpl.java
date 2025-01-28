package org.ms.timepro.manager.services;

import org.jwt.security.dto.JwtUserPrincipal;
import org.jwt.security.security.JwtTokenProvider;
import org.ms.timepro.manager.dto.AuthRequestDto;
import org.ms.timepro.manager.dto.AuthResponseDto;
import org.ms.timepro.manager.exception.UserNotAuthException;
import org.ms.timepro.manager.log.Logger;
import org.ms.timepro.manager.utils.ConstantUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServicesImpl {

	private final AuthenticationManager securityAuthenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Logger
	public AuthResponseDto authenticateUser(AuthRequestDto dto)
			throws UserNotAuthException {
		AuthResponseDto result = null;

        try {
			Authentication authentication = authenticateUser(dto.getUsername(), dto.getPassword());
			JwtUserPrincipal userPrincipal = (JwtUserPrincipal) authentication.getPrincipal();

			checkUserAuthStatus(userPrincipal);

			result = createAuthResponse(userPrincipal, authentication);

		} catch (BadCredentialsException | UserNotAuthException e) {
			throw new UserNotAuthException(e.getMessage());
		} catch (Exception e) {
			throw new UserNotAuthException(e.getMessage());
		}
		return result;
	}

	@Logger
	private Authentication authenticateUser(String userAndPassword, String password) {
		return securityAuthenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userAndPassword, password));
	}

	@Logger
	private void checkUserAuthStatus(JwtUserPrincipal userPrincipal) throws UserNotAuthException {
		if (Boolean.TRUE.equals(userPrincipal.getIsActive())) {
			throw new UserNotAuthException(ConstantUtil.USR_BLOCKED_STR);
		}
		if (Boolean.FALSE.equals(userPrincipal.getIsActive())) {
			throw new UserNotAuthException(ConstantUtil.USR_INACTIVE);
		}
	}

	@Logger
	private AuthResponseDto createAuthResponse(JwtUserPrincipal userPrincipal, Authentication authentication)
			throws Exception {
		AuthResponseDto authPass = new AuthResponseDto();

		authPass.setToken(jwtTokenProvider.generateToken(authentication));
		authPass.setUsername(userPrincipal.getUsername());
		authPass.setFullName(userPrincipal.getFullName());
		authPass.setId(userPrincipal.getIdUsuario());
		//authPass.setIsMfa(userPrincipal.getIsMfa());

		return authPass;
	}

}
