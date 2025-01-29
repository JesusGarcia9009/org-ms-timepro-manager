package org.ms.timepro.manager.services;

import org.ms.timepro.manager.dto.AuthRequestDto;
import org.ms.timepro.manager.dto.AuthResponseDto;
import org.ms.timepro.manager.dto.request.RegisterUserRequestDTO;
import org.ms.timepro.manager.dto.response.UserResponseDTO;
import org.ms.timepro.manager.exception.UserNotAuthException;

/**
 * Interfaz que define el servicio de autenticación de usuarios.
 * 
 * @author Jesus garcia
 * @since 0.0.1
 * @version jdk-21
 */
public interface AuthLoginService {

    /**
     * Autentica un usuario y devuelve la respuesta de autenticación.
     *
     * @param dto El objeto AuthRequestDTO que contiene la información de autenticación.
     * @return La respuesta de autenticación en forma de AuthResponseDto.
     * @throws UserNotAuthException Si la autenticación del usuario falla.
     */
	AuthResponseDto authenticateUser(AuthRequestDto dto) throws UserNotAuthException;

    UserResponseDTO registerUser(RegisterUserRequestDTO dto) throws UserNotAuthException;

}
