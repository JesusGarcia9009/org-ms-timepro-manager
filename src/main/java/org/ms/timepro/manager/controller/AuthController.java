package org.ms.timepro.manager.controller;

import org.modelmapper.ModelMapper;
import org.ms.timepro.manager.dto.AuthRequestDto;
import org.ms.timepro.manager.dto.AuthResponseDto;
import org.ms.timepro.manager.dto.request.RegisterUserRequestDTO;
import org.ms.timepro.manager.dto.response.UserResponseDTO;
import org.ms.timepro.manager.exception.UserNotAuthException;
import org.ms.timepro.manager.log.Logger;
import org.ms.timepro.manager.services.AuthServicesImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthServicesImpl authService;
	private final ModelMapper modelMapper;

	@Logger
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody AuthRequestDto dto)
			throws UserNotAuthException
	{
		return ResponseEntity.ok(authService.authenticateUser(dto));
	}


	@Logger
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterUserRequestDTO dto) throws UserNotAuthException {


		return ResponseEntity.ok(authService.registerUser(dto));
	}

}
