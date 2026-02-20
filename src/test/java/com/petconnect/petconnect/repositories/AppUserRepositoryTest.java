package com.petconnect.petconnect.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.petconnect.petconnect.models.AppUser;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AppUserRepositoryTest {

  @Mock private AppUserRepository appUserRepository;

  private AppUser mockUser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockUser = new AppUser();
    mockUser.setId(1);
    mockUser.setEmail("test@example.com");
    mockUser.setRole("USER");
    mockUser.setPlz("12345");
  }

  @Test
  void testFindByEmail() {
    when(appUserRepository.findByEmail("test@example.com")).thenReturn(mockUser);

    AppUser result = appUserRepository.findByEmail("test@example.com");

    assertThat(result).isNotNull();
    assertThat(result.getEmail()).isEqualTo("test@example.com");

    verify(appUserRepository, times(1)).findByEmail("test@example.com");
  }

  @Test
  void testFindById() {
    when(appUserRepository.findById(1)).thenReturn(Optional.of(mockUser));

    Optional<AppUser> result = appUserRepository.findById(1);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(1);

    verify(appUserRepository).findById(1);
  }

  @Test
  void testFindByRoleAndPlz() {
    when(appUserRepository.findByRoleIgnoreCaseAndPlz("USER", "12345"))
        .thenReturn(List.of(mockUser));

    List<AppUser> results = appUserRepository.findByRoleIgnoreCaseAndPlz("USER", "12345");

    assertThat(results).hasSize(1);
    assertThat(results.get(0).getPlz()).isEqualTo("12345");

    verify(appUserRepository).findByRoleIgnoreCaseAndPlz("USER", "12345");
  }

  @Test
  void testFindOptionalByEmail() {
    when(appUserRepository.findOptionalByEmail("test@example.com"))
        .thenReturn(Optional.of(mockUser));

    Optional<AppUser> result = appUserRepository.findOptionalByEmail("test@example.com");

    assertThat(result).isPresent();
    assertThat(result.get().getEmail()).isEqualTo("test@example.com");

    verify(appUserRepository).findOptionalByEmail("test@example.com");
  }
}
