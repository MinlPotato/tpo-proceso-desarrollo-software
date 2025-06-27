package com.uade.tpo.application.service.adapter.EmailAdapter;

import org.springframework.beans.factory.annotation.Value;
import com.uade.tpo.application.dto.NotificacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JavaEmailAdapter implements NotificacionEmailAdapter {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviar(NotificacionDTO notificacion) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificacion.getEmail());
        message.setSubject(notificacion.getTitulo());
        message.setText(notificacion.getMensaje());
        mailSender.send(message);

    }
}
