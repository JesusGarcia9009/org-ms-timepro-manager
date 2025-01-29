package org.ms.timepro.manager.services;

import io.jsonwebtoken.security.Password;
import org.jwt.security.dto.JwtUserPrincipal;
import org.jwt.security.entity.Profile;
import org.jwt.security.entity.Users;
import org.jwt.security.repository.ProfileRepository;
import org.jwt.security.repository.UserRepository;
import org.jwt.security.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.ms.timepro.manager.dto.AuthRequestDto;
import org.ms.timepro.manager.dto.AuthResponseDto;
import org.ms.timepro.manager.dto.request.RegisterUserRequestDTO;
import org.ms.timepro.manager.dto.response.UserResponseDTO;
import org.ms.timepro.manager.exception.UserNotAuthException;
import org.ms.timepro.manager.log.Logger;
import org.ms.timepro.manager.utils.ConstantUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServicesImpl implements AuthLoginService {

	private final AuthenticationManager securityAuthenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final ProfileRepository profileRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Logger
	@Override
	public AuthResponseDto authenticateUser(AuthRequestDto dto)
			throws UserNotAuthException {
		AuthResponseDto result = null;

        try {
			Authentication authentication = authenticateUser(dto.getUsername(), dto.getPassword());
			JwtUserPrincipal userPrincipal = (JwtUserPrincipal) authentication.getPrincipal();

			//checkUserAuthStatus(userPrincipal);

			result = createAuthResponse(userPrincipal, authentication);

		} catch (BadCredentialsException | UserNotAuthException e) {
			throw new UserNotAuthException(e.getMessage());
		} catch (Exception e) {
			throw new UserNotAuthException(e.getMessage());
		}
		return result;
	}

	@Logger
	@Override
	public UserResponseDTO registerUser(RegisterUserRequestDTO dto) throws UserNotAuthException {

		UserResponseDTO response = null;

 		Users users = Users.builder()
				.name(dto.getName())
				.username(dto.getUsername())
				.active(Boolean.TRUE)
				.cellphone(dto.getCellphone())
				.name(dto.getName())
				.email(dto.getEmail())
				.mailingAdd(dto.getMailingAdd())
				.lastName(dto.getLastName())
				.profile(profileRepository.findByProfileName(dto.getProfile()))
				.rut(dto.getRut())
				.pass(passwordEncoder.encode(dto.getPass())).build();
		 userRepository.save(users);

		response = modelMapper.map(users, UserResponseDTO.class);
		return response;
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
