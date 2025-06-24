package com.uade.tpo.application.service.strategy.notificacion;

import com.uade.tpo.application.dto.NotificacionDTO;
import com.uade.tpo.application.service.adapter.EmailAdapter.NotificacionEmailAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionEmail implements NotifiacionStrategy {

    @Autowired
    private NotificacionEmailAdapter notificacionEmailAdapter;

    @Override
    public void enviar(NotificacionDTO notificacion) {
        notificacionEmailAdapter.enviar(notificacion);
    }
}
