package com.uade.tpo.application.service.notificador;

import com.uade.tpo.application.dto.NotificacionDTO;
import com.uade.tpo.application.enums.FormaNotificar;
import com.uade.tpo.application.factory.NotificadorFactory;
import com.uade.tpo.application.service.strategy.notificacion.NotifiacionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificadorService implements INotificadorService {

    @Autowired
    private NotificadorFactory notificadorFactory;

    public void enviarNotificaion(FormaNotificar formaNotificar, NotificacionDTO notificacion) {
        NotifiacionStrategy notifiacionStrategy = notificadorFactory.crearEstrategiaNotificacion(formaNotificar);
        notifiacionStrategy.enviar(notificacion);
    }


}
