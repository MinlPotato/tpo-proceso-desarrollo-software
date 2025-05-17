package com.uade.tpo.application.service.jugador;

import com.uade.tpo.application.entity.Jugador;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface IJugadorService {

    List<Jugador> findAll();
    Optional<Jugador> findById(Long id);
    Jugador save(Jugador entity);
    void deleteById(Long id);
}
