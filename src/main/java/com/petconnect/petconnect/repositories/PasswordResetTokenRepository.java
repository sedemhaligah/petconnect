package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  PasswordResetToken findByToken(String token);

  PasswordResetToken findByUser(AppUser user);
}
