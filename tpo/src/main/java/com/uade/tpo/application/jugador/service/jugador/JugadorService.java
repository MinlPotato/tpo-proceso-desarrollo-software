package com.uade.tpo.application.jugador.service.jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.uade.tpo.application.jugador.entity.Jugador;
import com.uade.tpo.application.jugador.repository.JugadorRepository;

@Service
public class JugadorService implements IJugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> findById(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador save(Jugador entity) {
        return jugadorRepository.save(entity);
    }

    public void deleteById(Long id) {
        jugadorRepository.deleteById(id);
    }
}
