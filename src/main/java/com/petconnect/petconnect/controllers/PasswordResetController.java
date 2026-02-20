package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.PasswordResetToken;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.PasswordResetTokenRepository;
import com.petconnect.petconnect.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordResetController {

  @Autowired private AppUserRepository userRepo;

  @Autowired private PasswordResetTokenRepository tokenRepo;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private EmailService emailService;

  @GetMapping("/forgot-password")
  public String forgotPasswordForm() {
    return "forgotPassword";
  }

  @PostMapping("/forgot-password")
  public String handleForgotPassword(
      @RequestParam("email") String email, HttpServletRequest request, Model model) {
    AppUser user = userRepo.findByEmail(email);
    if (user != null) {
      // Check if a token already exists for the user
      PasswordResetToken existingToken = tokenRepo.findByUser(user);
      if (existingToken != null && existingToken.getExpiryDate().after(new Date())) {
        model.addAttribute(
            "message", "Ein Token wurde bereits gesendet. Bitte warten Sie, bis es abläuft.");
        return "forgotPassword";
      }

      // If no token exists, or it has expired, create a new token
      String token = UUID.randomUUID().toString();
      PasswordResetToken resetToken = new PasswordResetToken();
      resetToken.setToken(token);
      resetToken.setUser(user);
      resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60)); // 1 hour
      tokenRepo.save(resetToken);

      String resetLink =
          request.getRequestURL().toString().replace(request.getServletPath(), "")
              + "/reset-password?token="
              + token;

      // Log reset link (for testing purposes)
      System.out.println("Reset link: " + resetLink);
      emailService.sendResetLink(email, resetLink);
    }

    model.addAttribute(
        "message",
        "Wenn die E-Mail-Adresse registriert ist, erhalten Sie einen Link zum Zurücksetzen.");
    return "forgotPassword";
  }

  @GetMapping("/reset-password")
  public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
    PasswordResetToken resetToken = tokenRepo.findByToken(token);

    if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
      model.addAttribute("message", "Ungültiger oder abgelaufener Token.");
      return "resetPassword";
    }

    model.addAttribute("token", token);
    return "resetPassword";
  }

  @PostMapping("/reset-password")
  public String handleResetPassword(
      @RequestParam("token") String token,
      @RequestParam("password") String password,
      @RequestParam("confirmPassword") String confirmPassword,
      Model model) {

    if (!password.equals(confirmPassword)) {
      model.addAttribute("message", "Passwörter stimmen nicht überein.");
      model.addAttribute("token", token);
      return "resetPassword";
    }

    PasswordResetToken resetToken = tokenRepo.findByToken(token);

    if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
      model.addAttribute("message", "Ungültiger oder abgelaufener Token.");
      return "resetPassword";
    }

    AppUser user = resetToken.getUser();
    user.setPassword(passwordEncoder.encode(password));
    userRepo.save(user);

    tokenRepo.delete(resetToken); // invalidate token

    model.addAttribute("message", "Passwort erfolgreich zurückgesetzt.");
    return "login";
  }
}
