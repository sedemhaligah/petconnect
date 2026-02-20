package com.petconnect.petconnect.integration;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.repositories.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AppUserIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private AppUserRepository appUserRepository;

  @BeforeEach
  void setup() {
    appUserRepository.deleteAll();
  }

  @Test
  void testRegisterNewUser() throws Exception {
    mockMvc
        .perform(
            post("/register")
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("email", "integration@test.com")
                .param("password", "pass123")
                .param("confirmPassword", "pass123")
                .param("role", "USER")
                .param("phone", "12345")
                .param("address", "Teststraße 1")
                .param("plz", "12345")
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attribute("success", true));

    // Ensure the user is actually persisted
    AppUser user = appUserRepository.findByEmail("integration@test.com");
    assertNotNull(user);
    assertEquals("Test", user.getFirstName());
  }

  @Test
  void testViewOtherUserProfile_redirectsWhenUnauthenticated() throws Exception {
    AppUser user = new AppUser();
    user.setEmail("guest@test.com");
    user.setFirstName("Guest");
    user.setLastName("User");
    appUserRepository.save(user);

    mockMvc
        .perform(get("/profile/" + user.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  @WithMockUser(username = "testuser@example.com") // simulate logged-in user
  void testBetreuerFindenByPlz() throws Exception {
    AppUser betreuer = new AppUser();
    betreuer.setFirstName("Max");
    betreuer.setLastName("Mustermann");
    betreuer.setEmail("max@petconnect.de");
    betreuer.setRole("BETREUER");
    betreuer.setPlz("12345");
    appUserRepository.save(betreuer);

    mockMvc
        .perform(get("/betreuerfinden").param("plz", "12345"))
        .andExpect(status().isOk())
        .andExpect(view().name("betreuerfinden"))
        .andExpect(model().attributeExists("betreuerList"));
  }

  @Test
  @WithMockUser(username = "user@example.com")
  void testViewOwnProfile() throws Exception {
    AppUser user = new AppUser();
    user.setEmail("user@example.com");
    user.setFirstName("Alice");
    user.setLastName("Test");
    user.setRole("USER");
    user.setPlz("12345");
    appUserRepository.save(user);

    mockMvc
        .perform(get("/profile/" + user.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("Profilseite"))
        .andExpect(model().attributeExists("user"))
        .andExpect(
            model()
                .attribute(
                    "user",
                    allOf(
                        hasProperty("email", is("user@example.com")),
                        hasProperty("firstName", is("Alice")),
                        hasProperty("lastName", is("Test")),
                        hasProperty("role", is("USER")),
                        hasProperty("plz", is("12345")))));
  }
}
