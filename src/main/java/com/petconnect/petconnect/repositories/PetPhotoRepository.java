package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.PetPhoto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {
  List<PetPhoto> findByUser(AppUser user);

  int countByUser(AppUser user);
}
