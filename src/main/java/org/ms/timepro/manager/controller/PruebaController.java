package org.ms.timepro.manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jwt.security.anotations.TokenUsuario;
import org.jwt.security.dto.JwtUserPrincipal;
import org.ms.timepro.manager.exception.UserNotAuthException;
import org.ms.timepro.manager.log.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/prueba")
public class PruebaController {

    @Logger
    @GetMapping("/login")
    public ResponseEntity<JwtUserPrincipal> authenticateUser(@TokenUsuario JwtUserPrincipal userToken)
            throws UserNotAuthException
    {
        return ResponseEntity.ok(userToken);
    }
}
