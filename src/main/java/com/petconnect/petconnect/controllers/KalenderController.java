package com.petconnect.petconnect.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KalenderController {

  @Value("${google.calendar.api-key:}")
  private String googleCalendarApiKey;

  @GetMapping("/Kalender")
  public String Klaender(Model model) {
    model.addAttribute("googleApiKey", googleCalendarApiKey);
    return "Kalender";
  }
}
