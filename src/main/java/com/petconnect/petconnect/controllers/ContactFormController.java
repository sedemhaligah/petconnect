package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.services.EmailService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactFormController {

  @Autowired private EmailService emailService;

  @PostMapping("/submit")
  public ResponseEntity<?> submitContactForm(@RequestBody Map<String, String> data) {
    String firstName = data.get("firstName");
    String lastName = data.get("lastName");
    String email = data.get("email");
    String city = data.get("city");
    String message = data.get("message");

    String subject = "Neue Kontaktanfrage von " + firstName + " " + lastName;
    String body =
        String.format(
            """
                Vorname: %s
                Nachname: %s
                E-Mail: %s

                Stadt: %s

                Nachricht:
                %s
                """,
            firstName, lastName, email, city, message);

    emailService.sendContactMessage(subject, body);

    return ResponseEntity.ok("Kontaktanfrage erfolgreich gesendet.");
  }
}
