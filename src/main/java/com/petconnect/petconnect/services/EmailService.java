package com.petconnect.petconnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired private JavaMailSender mailSender;

  public void sendResetLink(String toEmail, String resetLink) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("swe20251@gmail.com");
    message.setTo(toEmail);
    message.setSubject("Passwort zurücksetzen - PetCareConnect");
    message.setText(
        "Hallo,\n\nBitte klicken Sie auf den folgenden Link, um Ihr Passwort zurückzusetzen:\n\n"
            + resetLink
            + "\n\nWenn Sie dies nicht angefordert haben, ignorieren Sie bitte diese Nachricht.");

    mailSender.send(message);
  }

  public void sendContactMessage(String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("swe20251@gmail.com");
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
  }
}
