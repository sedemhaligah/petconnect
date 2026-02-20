package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.UserAvailability;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAvailabilityRepository extends JpaRepository<UserAvailability, Long> {
  List<UserAvailability> findByUserId(int userId);

  UserAvailability findByUserIdAndDate(int userId, LocalDate date);

  void deleteByUserIdAndDate(int userId, LocalDate date);
}
