package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.Bewertung;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BewertungRepository extends JpaRepository<Bewertung, Long> {
  List<Bewertung> findByBewerteterUserOrderByErstellungsdatumDesc(AppUser user);
}
