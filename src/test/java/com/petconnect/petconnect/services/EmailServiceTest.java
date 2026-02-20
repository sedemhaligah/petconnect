package com.petconnect.petconnect.services;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailServiceTest {

  @Mock private JavaMailSender mailSender;

  @InjectMocks private EmailService emailService;

  @Captor private ArgumentCaptor<SimpleMailMessage> messageCaptor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void sendResetLink_shouldSendEmailWithCorrectContent() {
    String toEmail = "user@example.com";
    String resetLink = "https://example.com/reset?token=abc123";

    emailService.sendResetLink(toEmail, resetLink);

    verify(mailSender).send(messageCaptor.capture());
    SimpleMailMessage message = messageCaptor.getValue();

    assert message.getTo()[0].equals(toEmail);
    assert message.getSubject().equals("Passwort zurücksetzen - PetCareConnect");
    assert message.getText().contains(resetLink);
    assert message.getFrom().equals("swe20251@gmail.com");
  }

  @Test
  void sendContactMessage_shouldSendContactEmail() {
    String subject = "Test Subject";
    String body = "This is a test message from contact form.";

    emailService.sendContactMessage(subject, body);

    verify(mailSender).send(messageCaptor.capture());
    SimpleMailMessage message = messageCaptor.getValue();

    assert message.getTo()[0].equals("swe20251@gmail.com");
    assert message.getSubject().equals(subject);
    assert message.getText().equals(body);
  }
}
