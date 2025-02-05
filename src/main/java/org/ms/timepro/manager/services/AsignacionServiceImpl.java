package org.ms.timepro.manager.services;

import lombok.RequiredArgsConstructor;
import org.ms.timepro.manager.entity.Asignacion;
import org.ms.timepro.manager.repository.AsignacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignacionServiceImpl implements AsignacionService {
    private final AsignacionRepository asignacionRepository;

    @Override
    public List<Asignacion> findAllAsignaciones() {
       return asignacionRepository.findAll();
    }
}
