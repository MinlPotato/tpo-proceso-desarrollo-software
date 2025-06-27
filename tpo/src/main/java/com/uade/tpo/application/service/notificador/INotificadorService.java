package com.uade.tpo.application.service.notificador;

import com.uade.tpo.application.dto.NotificacionDTO;
import com.uade.tpo.application.enums.FormaNotificar;

public interface INotificadorService {
    void enviarNotificaion(FormaNotificar formaNotificar, NotificacionDTO notificacion);
}
