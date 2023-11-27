package com.barberia.barberia.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    JavaMailSender javaMailSender;
    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendMessage(String email, String messageEmail){
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
           message.setSubject("prueba");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);

        }catch (MessagingException e){
            throw new RuntimeException(e);

        }
    }
}
