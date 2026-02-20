package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.AppUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
  public AppUser findByEmail(String email);

  Optional<AppUser> findById(Integer id);

  List<AppUser> findByRoleIgnoreCaseAndPlz(String role, String plz);

  Optional<AppUser> findOptionalByEmail(String email);

  List<AppUser> findByPlzAndIdNot(String plz, int id);

  List<AppUser> findByPlzAndRoleAndIdNot(String plz, String role, int id);
}
