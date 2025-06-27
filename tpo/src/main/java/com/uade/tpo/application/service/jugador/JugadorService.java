package com.uade.tpo.application.service.jugador;

import com.uade.tpo.application.dto.JugadorCreateDTO;
import com.uade.tpo.application.dto.JugadorDTO;
import com.uade.tpo.application.dto.NivelCreateDTO;
import com.uade.tpo.application.dto.NivelDTO;
import com.uade.tpo.application.entity.Nivel;
import com.uade.tpo.application.entity.User;
import com.uade.tpo.application.exception.ResourceNotFoundException;
import com.uade.tpo.application.repository.UserRepository;
import com.uade.tpo.application.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import com.uade.tpo.application.entity.Jugador;
import com.uade.tpo.application.repository.JugadorRepository;

@Service
public class JugadorService implements IJugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private UserService userService;


    @Override
    public List<Jugador> getJugadores() {
        return jugadorRepository.findAll();
    }

    @Override
    public Jugador getJugadorById(Long id) {
        return jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));
    }

    @Override
    public Jugador createJugador(JugadorCreateDTO requestBody) {
        User user = userService.getCurrentUser();

        Jugador jugador = new Jugador(requestBody, user);

        return jugadorRepository.save(jugador);
    }

    @Override
    public Jugador updateJugador(Long id, JugadorCreateDTO requestBody) {

        User user = userService.getCurrentUser();
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));
        validarUsuario(jugador, user);

        jugador.setNombre(requestBody.getNombre());
        jugador.setEmail(requestBody.getEmail());
        jugador.setUbicacion(requestBody.getUbicacion());
        jugador.setFormaNotificar(requestBody.getFormaNotificar());

        return jugadorRepository.save(jugador);
    }

    @Override
    public void eliminarJugador(Long id) {

        User user = userService.getCurrentUser();
        Jugador jugador = jugadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Jugador not found with id " + id));
        validarUsuario(jugador, user);

        jugadorRepository.deleteById(id);
    }

     public void validarUsuario(Jugador jugador, User user) {
        if (!jugador.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Usuario denegado");
        }
    }

}
