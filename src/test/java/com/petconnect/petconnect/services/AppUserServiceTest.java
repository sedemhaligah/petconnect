package com.petconnect.petconnect.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.repositories.AppUserRepository;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

class AppUserServiceTest {

  @InjectMocks private AppUserService appUserService;

  @Mock private AppUserRepository repo;

  @Mock private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetUserByEmail_returnsUser() {
    AppUser mockUser = new AppUser();
    mockUser.setEmail("test@example.com");

    when(repo.findByEmail("test@example.com")).thenReturn(mockUser);

    AppUser result = appUserService.getUserByEmail("test@example.com");

    assertNotNull(result);
    assertEquals("test@example.com", result.getEmail());
  }

  @Test
  void testGetUserById_returnsUser() {
    AppUser mockUser = new AppUser();
    mockUser.setId(1);

    when(repo.findById(1)).thenReturn(Optional.of(mockUser));

    AppUser result = appUserService.getUserById(1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  void testGetAllBetreuer_filtersCorrectly() {
    AppUser betreuer = new AppUser();
    betreuer.setRole("BETREUER");

    AppUser kunde = new AppUser();
    kunde.setRole("KUNDE");

    when(repo.findAll()).thenReturn(List.of(betreuer, kunde));

    List<AppUser> result = appUserService.getAllBetreuer();

    assertEquals(1, result.size());
    assertEquals("BETREUER", result.get(0).getRole());
  }

  @Test
  void testUpdateUserProfile_updatesCorrectly() {
    AppUser currentUser = new AppUser();
    currentUser.setFirstName("Old");

    AppUser updatedUser = new AppUser();
    updatedUser.setFirstName("New");
    updatedUser.setLastName("Last");
    updatedUser.setPhone("123");
    updatedUser.setAddress("Street");
    updatedUser.setPlz("12345");
    updatedUser.setExperience("2 years");

    when(repo.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

    AppUser result = appUserService.updateUserProfile(currentUser, updatedUser, "About Me");

    assertEquals("New", result.getFirstName());
    assertEquals("About Me", result.getAboutMe());
    assertEquals("2 years", result.getExperience());
  }
}
