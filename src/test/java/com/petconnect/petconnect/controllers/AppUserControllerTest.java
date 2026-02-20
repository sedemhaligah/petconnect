package com.petconnect.petconnect.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.PetPhotoRepository;
import com.petconnect.petconnect.services.AppUserService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AppUserControllerTest {

  @Mock private AppUserRepository appUserRepository;
  @Mock private AppUserService appUserService;
  @Mock private PetPhotoRepository petPhotoRepository;

  @InjectMocks private AppUserController controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void testGetRegisterPage() throws Exception {
    mockMvc
        .perform(get("/register"))
        .andExpect(status().isOk())
        .andExpect(view().name("anmelden"))
        .andExpect(model().attributeExists("registerDto"));
  }

  @Test
  void testPostRegister_Success() throws Exception {
    // Mock repository to say email doesn't exist yet
    when(appUserRepository.findByEmail("test@example.com")).thenReturn(null);

    mockMvc
        .perform(
            post("/register")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "test@example.com")
                .param("password", "password123")
                .param("confirmPassword", "password123")
                .param("role", "USER")
                .param("phone", "1234567890") // add if required
                .param("address", "123 Street") // add if required
                .param("plz", "12345")) // add if required
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attributeExists("registerDto"))
        .andExpect(
            model().attribute("success", true)); // your controller adds success=true on success

    // Verify save was called exactly once
    verify(appUserRepository, times(1)).save(any(AppUser.class));
  }

  @Test
  void testShowProfilePage_UserFound() throws Exception {
    String email = "user@example.com";
    AppUser user = new AppUser();
    user.setEmail(email);

    Authentication auth = mock(Authentication.class);
    SecurityContext context = mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(context);
    when(auth.getName()).thenReturn(email);

    when(appUserService.getUserByEmail(email)).thenReturn(user);
    when(petPhotoRepository.findByUser(user)).thenReturn(new ArrayList<>());

    mockMvc
        .perform(get("/profile"))
        .andExpect(status().isOk())
        .andExpect(view().name("Profilseite"))
        .andExpect(model().attributeExists("user"))
        .andExpect(model().attributeExists("petPhotos"));
  }

  @Test
  void testViewUserProfile_NotFound() throws Exception {
    when(appUserService.getUserById(99)).thenReturn(null);

    mockMvc
        .perform(get("/profile/{id}", 99))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/error"));
  }

  @Test
  void testAddService_Success() throws Exception {
    String email = "serviceuser@example.com";
    AppUser user = new AppUser();
    user.setEmail(email);

    Authentication auth = mock(Authentication.class);
    SecurityContext context = mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(context);
    when(auth.getName()).thenReturn(email);

    when(appUserService.getUserByEmail(email)).thenReturn(user);

    mockMvc
        .perform(
            post("/profile/service/add")
                .param("title", "Dog Walking")
                .param("price", "15.0")
                .param("description", "Walk your dog"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/profile/edit"));

    verify(appUserRepository).save(user);
  }
}
