package com.petconnect.petconnect.services;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.UserAvailability;
import com.petconnect.petconnect.repositories.UserAvailabilityRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class UserAvailabilityServiceTest {
  @Mock private UserAvailabilityRepository userAvailabilityRepository;
  @InjectMocks private UserAvailabilityService userAvailabilityService;
  private AppUser user;
  private LocalDate testDate;

  @BeforeEach
  void setUp() {
    this.user = new AppUser();
    this.user.setId(1);
    this.testDate = LocalDate.of(2025, 5, 16);
  }

  @Test
  void testAddAvailability() {
    UserAvailability mockAvailability = new UserAvailability(this.user, this.testDate);
    Mockito.when(
            (UserAvailability)
                this.userAvailabilityRepository.save(
                    (UserAvailability) Mockito.any(UserAvailability.class)))
        .thenReturn(mockAvailability);
    UserAvailability result =
        this.userAvailabilityService.addAvailability(this.user, this.testDate);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(this.testDate, result.getDate());
    Assertions.assertEquals(this.user, result.getUser());
    ((UserAvailabilityRepository) Mockito.verify(this.userAvailabilityRepository, Mockito.times(1)))
        .save((UserAvailability) Mockito.any(UserAvailability.class));
  }

  @Test
  void testGetAvailabilityByUser() {
    List<UserAvailability> mockList =
        Arrays.asList(
            new UserAvailability(this.user, this.testDate),
            new UserAvailability(this.user, this.testDate.plusDays(1L)));
    Mockito.when(this.userAvailabilityRepository.findByUserId(1)).thenReturn(mockList);
    List<UserAvailability> result = this.userAvailabilityService.getAvailabilityByUser(1);
    Assertions.assertEquals(2, result.size());
    ((UserAvailabilityRepository) Mockito.verify(this.userAvailabilityRepository, Mockito.times(1)))
        .findByUserId(1);
  }

  @Test
  void testRemoveAvailability() {
    this.userAvailabilityService.removeAvailability(1, this.testDate);
    ((UserAvailabilityRepository) Mockito.verify(this.userAvailabilityRepository, Mockito.times(1)))
        .deleteByUserIdAndDate(1, this.testDate);
  }
}
