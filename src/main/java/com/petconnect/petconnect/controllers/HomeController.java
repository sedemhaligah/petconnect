package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @Autowired private AppUserService appUserService;

  @GetMapping("/")
  public String home(Model model) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser user = appUserService.getUserByEmail(email);
    model.addAttribute("user", user);
    return "Homepage";
  }
}
