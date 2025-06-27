package com.uade.tpo.application.factory;

import com.uade.tpo.application.enums.FormaNotificar;
import com.uade.tpo.application.service.strategy.notificacion.FireBaseNotificacionPush;
import com.uade.tpo.application.service.strategy.notificacion.NotifiacionStrategy;
import com.uade.tpo.application.service.strategy.notificacion.NotificacionEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificadorFactory {

    private final NotificacionEmail notificacionEmail;
    private final FireBaseNotificacionPush fireBaseNotificacionPush;

    @Autowired
    public NotificadorFactory(
        NotificacionEmail notificacionEmail,
        FireBaseNotificacionPush fireBaseNotificacionPush
    ) {
        this.notificacionEmail = notificacionEmail;
        this.fireBaseNotificacionPush = fireBaseNotificacionPush;
    }

    public NotifiacionStrategy crearEstrategiaNotificacion(FormaNotificar formaNotificar) {
        return switch (formaNotificar) {
            case PUSH -> fireBaseNotificacionPush;
            case EMAIL -> notificacionEmail;
        };
    }

    public static FormaNotificar obtenerEnumNotificacion(NotifiacionStrategy estrategia) {
        if (estrategia instanceof FireBaseNotificacionPush) return FormaNotificar.PUSH;
        if (estrategia instanceof NotificacionEmail) return FormaNotificar.EMAIL;
        throw new IllegalArgumentException("Estrategia de Notificacion no encontrada.");
    }

}
