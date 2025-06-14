package com.uade.tpo.application.controller;

import com.uade.tpo.application.dto.NotificacionDTO;
import com.uade.tpo.application.enums.FormaNotificar;
import com.uade.tpo.application.service.notificador.NotificadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacion")
public class NotificadorController {

    @Autowired
    private NotificadorService notificadorService;

    @GetMapping
    public String sendEmail(@RequestBody NotificacionDTO notificacionDTO, @RequestParam FormaNotificar strategy) {
        notificadorService.enviarNotificaion(strategy, notificacionDTO);
        return "sent!";
    }

}
