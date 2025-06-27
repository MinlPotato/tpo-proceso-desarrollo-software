package com.uade.tpo.application.service.strategy.notificacion;

import com.uade.tpo.application.dto.NotificacionDTO;

public interface NotifiacionStrategy {

  public void enviar(NotificacionDTO notificacion);

}
