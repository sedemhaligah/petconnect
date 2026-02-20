package com.petconnect.petconnect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KalenderController {

  @GetMapping("/Kalender")
  public String Klaender() {
    return "Kalender";
  }
}
