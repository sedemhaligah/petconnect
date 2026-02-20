package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@Controller
public class HomeController {

  @Autowired private AppUserService appUserService;

  @GetMapping("/")
  public String home(Model model, Principal principal) {
    AppUser user = null;

    if (principal != null) {
      // principal.getName() should be the email (because you set usernameParameter("email"))
      user = appUserService.getUserByEmail(principal.getName());
    }

    model.addAttribute("user", user); // can be null (logged out or user not found)
    return "homepage";
  }

}
