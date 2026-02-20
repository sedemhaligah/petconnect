package com.petconnect.petconnect.services;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.repositories.AppUserRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
  @Autowired private AppUserRepository repo;

  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AppUser appUser = repo.findByEmail(email);

    if (appUser != null) {
      var springUser =
          User.withUsername(appUser.getEmail())
              .password(appUser.getPassword())
              .roles(appUser.getRole())
              .build();
      return springUser;
    }

    return null;
  }

  // Get user by email
  public AppUser getUserByEmail(String email) {
    return repo.findByEmail(email);
  }

  // Get user by ID
  @Nullable
  public AppUser getUserById(int id) {
    return repo.findById(id).orElse(null);
  }

  // Update user profile
  public AppUser updateUserProfile(AppUser currentUser, AppUser updatedUser, String aboutMe) {
    // Update basic user information
    currentUser.setFirstName(updatedUser.getFirstName());
    currentUser.setLastName(updatedUser.getLastName());
    currentUser.setPhone(updatedUser.getPhone());
    currentUser.setAddress(updatedUser.getAddress());
    currentUser.setPlz(updatedUser.getPlz());

    // Update experience (if not already handled)
    currentUser.setExperience(updatedUser.getExperience());

    // Set about me text if provided
    if (aboutMe != null && !aboutMe.isEmpty()) {
      currentUser.setAboutMe(aboutMe);
    }

    // Update timestamp
    currentUser.setUpdatedAt(new Date());

    // Save and return updated user
    return repo.save(currentUser);
  }

  public List<AppUser> getAllBetreuer() {
    return repo.findAll().stream()
        .filter(user -> "BETREUER".equalsIgnoreCase(user.getRole()))
        .collect(Collectors.toList());
  }
}
