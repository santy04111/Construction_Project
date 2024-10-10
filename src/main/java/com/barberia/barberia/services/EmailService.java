package com.barberia.barberia.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmailwithAttachment(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom("ingescato@gmail.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body);

            // Adjuntar archivo si es necesario

            javaMailSender.send(message);

            return "Correo enviado exitosamente con archivo adjunto";

        } catch (MessagingException | MailException e) {
            // Log the exception or handle it as needed
            e.printStackTrace(); // Log the exception to console for now

            return "Error al enviar el correo con archivo adjunto";
        }
    }
}
