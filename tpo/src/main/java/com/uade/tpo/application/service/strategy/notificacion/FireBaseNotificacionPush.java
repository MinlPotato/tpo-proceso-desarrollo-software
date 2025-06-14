package com.uade.tpo.application.service.strategy.notificacion;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.uade.tpo.application.dto.NotificacionDTO;
import org.springframework.stereotype.Service;

@Service
public class FireBaseNotificacionPush implements NotifiacionStrategy {

    @Override
    public void enviar(NotificacionDTO notificacionDTO) {

        Notification notificacion = Notification.builder()
            .setTitle(notificacionDTO.getTitulo())
            .setBody(notificacionDTO.getTitulo())
            .build();

        Message mensaje = Message.builder()
            .setToken("token")
            .setNotification(notificacion)
            .build();

        try {
            FirebaseMessaging.getInstance().send(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
