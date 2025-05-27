package com.uade.tpo.application.deporte.service;
import org.springframework.stereotype.Service;
import com.uade.tpo.application.deporte.repository.DeporteRepository;
import com.uade.tpo.application.deporte.entity.Deporte;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;  
@Service
public class DeporteService {
    @Autowired
    private DeporteRepository deporteRepository;

    public List<Deporte> findAll() {
        return deporteRepository.findAll();
    }

    public Optional<Deporte> findById(Long id) {
        return deporteRepository.findById(id);
    }   
}
