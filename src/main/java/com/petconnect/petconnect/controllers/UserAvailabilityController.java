package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.UserAvailability;
import com.petconnect.petconnect.models.UserAvailabilityDTO;
import com.petconnect.petconnect.repositories.UserAvailabilityRepository;
import com.petconnect.petconnect.services.AppUserService;
import com.petconnect.petconnect.services.UserAvailabilityService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/availability")
public class UserAvailabilityController {

  private final UserAvailabilityService userAvailabilityService;

  @Autowired private UserAvailabilityRepository userAvailabilityRepository;

  @Autowired private AppUserService appUserService;

  @Autowired
  public UserAvailabilityController(UserAvailabilityService userAvailabilityService) {
    this.userAvailabilityService = userAvailabilityService;
  }

  // new availability for a user
  @PostMapping("/add")
  public UserAvailability addAvailability(@RequestBody UserAvailabilityDTO request) {
    LocalDate parsedDate = LocalDate.parse(request.getDate());
    AppUser user = appUserService.getUserById(request.getUserId());

    if (user == null) {
      throw new RuntimeException("User not found");
    }

    return userAvailabilityService.addAvailability(user, parsedDate);
  }

  // all availability for a user
  @GetMapping("/{userId}")
  public List<UserAvailability> getAvailability(@PathVariable int userId) {
    return userAvailabilityService.getAvailabilityByUser(userId);
  }

  // remove availability for a user
  @DeleteMapping("/remove")
  public void removeAvailability(@RequestParam int userId, @RequestParam String date) {
    LocalDate parsedDate;
    try {
      parsedDate = LocalDate.parse(date);
    } catch (DateTimeParseException e) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Invalid date format. Expected: YYYY-MM-DD");
    }

    AppUser user = appUserService.getUserById(userId);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    userAvailabilityService.removeAvailability(userId, parsedDate);
  }
}
