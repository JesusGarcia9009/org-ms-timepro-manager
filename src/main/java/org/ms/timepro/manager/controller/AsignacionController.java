package org.ms.timepro.manager.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ms.timepro.manager.entity.Asignacion;
import org.ms.timepro.manager.log.Logger;
import org.ms.timepro.manager.services.AsignacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/asignacion")
public class AsignacionController {

    private final AsignacionService asignacionService;

    @Logger
    @GetMapping("/lista")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Asignacion>> lista() {

        return ResponseEntity.ok(asignacionService.findAllAsignaciones());
    }

    @Logger
    @GetMapping("/lista2")
    @PreAuthorize("hasRole('ROLE_CREATE')")
    public ResponseEntity<List<Asignacion>> listaConPermiso() {

        return ResponseEntity.ok(asignacionService.findAllAsignaciones());
    }



}
