package com.dobe.locmns.services.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${admin.email}")
    private final String email;

    public void sendConfirmationEmail(String utilisateur, String Email) {
        String subject = "Confirmation de demande de prêt de matériel";
        String message = "Une nouvelle demande de prêt de matériel a été effectuée par l'utilisateur : \n" +
                "Nom : " + utilisateur + "\n" +
                "Adresse e-mail : " + Email;

        sendEmail(email, subject, message);
    }

    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail", e);
        }
    }
}
