package com.petconnect.petconnect.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.Bewertung;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.BewertungRepository;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BewertungController.class)
class BewertungControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BewertungRepository bewertungRepository;

  @MockBean private AppUserRepository appUserRepository;

  private AppUser user;
  private AppUser autor;
  private Bewertung bewertung;

  @BeforeEach
  void setup() {
    user = new AppUser();
    user.setId(1);
    user.setFirstName("Test");
    user.setLastName("User");

    autor = new AppUser();
    autor.setId(2);
    autor.setFirstName("Autor");
    autor.setLastName("Test");

    bewertung = new Bewertung();
    bewertung.setId(1);
    bewertung.setBewerteterUser(user);
    bewertung.setAutor(autor);
    bewertung.setRating(5);
    bewertung.setKommentar("Ausgezeichnet!");
  }

  @Test
  @WithMockUser(username = "mockuser@example.com")
  void testGetUserBewertung_ShouldReturnView() throws Exception {
    when(appUserRepository.findById(1)).thenReturn(Optional.of(user));
    when(bewertungRepository.findByBewerteterUserOrderByErstellungsdatumDesc(user))
        .thenReturn(Collections.singletonList(bewertung));

    mockMvc
        .perform(get("/user/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("Bewertungsseite"))
        .andExpect(model().attributeExists("neue_Bewertung"))
        .andExpect(model().attributeExists("Bewertung"))
        .andExpect(model().attributeExists("BewerteteUser"));
  }

  @Test
  @WithMockUser(username = "test@example.com")
  void testSaveBewertung_ShouldRedirect() throws Exception {
    Principal mockPrincipal = () -> "test@example.com";

    when(appUserRepository.findById(1)).thenReturn(Optional.of(user));
    when(appUserRepository.findByEmail("test@example.com")).thenReturn(autor);

    mockMvc
        .perform(
            post("/user/1")
                .param("rating", "5")
                .param("kommentar", "Ausgezeichnet!")
                .with(csrf())
                .principal(mockPrincipal))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/user/1"));

    verify(bewertungRepository, times(1)).save(any(Bewertung.class));
  }
}
