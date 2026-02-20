package com.petconnect.petconnect.services;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.UserAvailability;
import com.petconnect.petconnect.repositories.UserAvailabilityRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAvailabilityService {

  @Autowired private UserAvailabilityRepository userAvailabilityRepository;

  @Autowired
  public UserAvailabilityService(UserAvailabilityRepository userAvailabilityRepository) {
    this.userAvailabilityRepository = userAvailabilityRepository;
  }

  // Add a new availability
  public UserAvailability addAvailability(AppUser user, LocalDate date) {
    UserAvailability availability = new UserAvailability(user, date);
    availability.setUser(user);
    availability.setDate(date);
    return userAvailabilityRepository.save(availability);
  }

  // Get all availability dates for a user
  public List<UserAvailability> getAvailabilityByUser(int userId) {
    return userAvailabilityRepository.findByUserId(userId);
  }

  // Remove an availability date for a user
  @Transactional
  public void removeAvailability(int userId, LocalDate date) {
    userAvailabilityRepository.deleteByUserIdAndDate(userId, date);
  }
}
