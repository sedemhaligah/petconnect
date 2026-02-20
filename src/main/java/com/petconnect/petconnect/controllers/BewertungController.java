// BewertungController.java
package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.Bewertung;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.BewertungRepository;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BewertungController {

  private final BewertungRepository bewertungRepository;
  private final AppUserRepository appUserRepository;

  public BewertungController(
      BewertungRepository bewertungRepository, AppUserRepository appUserRepository) {
    this.bewertungRepository = bewertungRepository;
    this.appUserRepository = appUserRepository;
  }

  @GetMapping("/user/{user_Id}")
  public String getUserBewertung(@PathVariable int user_Id, Model model) {
    AppUser user = appUserRepository.findById(user_Id).orElseThrow();
    model.addAttribute("neue_Bewertung", new Bewertung());
    model.addAttribute(
        "Bewertung", bewertungRepository.findByBewerteterUserOrderByErstellungsdatumDesc(user));
    model.addAttribute("BewerteteUser", user); // <- This must not be null
    return "Bewertungsseite";
  }

  @PostMapping("/user/{user_id}")
  public String saveBewertung(
      @PathVariable int user_id, @ModelAttribute Bewertung bewertung, Principal principal) {

    AppUser user = appUserRepository.findById(user_id).orElseThrow();
    AppUser autor = appUserRepository.findByEmail(principal.getName());

    bewertung.setBewerteterUser(user);
    bewertung.setAutor(autor);
    bewertungRepository.save(bewertung);

    return "redirect:/user/" + user_id;
  }
}
