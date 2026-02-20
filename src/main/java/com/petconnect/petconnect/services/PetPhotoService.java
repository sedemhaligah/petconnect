package com.petconnect.petconnect.services;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.PetPhoto;
import com.petconnect.petconnect.repositories.PetPhotoRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetPhotoService {

  private static final String UPLOAD_DIR = "uploads";

  @Autowired private PetPhotoRepository petPhotoRepository;

  @Transactional
  public void savePetPhotos(List<MultipartFile> files, AppUser user) throws IOException {
    int existingPhotoCount = petPhotoRepository.countByUser(user);
    if (existingPhotoCount + files.size() > 4) {
      throw new IllegalStateException("Maximal 4 Bilder erlaubt.");
    }

    Path uploadDir = Paths.get(UPLOAD_DIR);
    if (!Files.exists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }

    for (MultipartFile file : files) {
      // Generate unique filename
      String uniqueFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
      Path filePath = uploadDir.resolve(uniqueFileName);

      // Save file to disk
      Files.write(filePath, file.getBytes());

      // Save filename & user to DB
      PetPhoto photo = new PetPhoto();
      photo.setFileName(uniqueFileName);
      photo.setUser(user);

      petPhotoRepository.save(photo);
    }
  }

  @Transactional
  public PetPhoto getPhotoById(Long id) {
    return petPhotoRepository.findById(id).orElse(null);
  }

  @Transactional
  public List<PetPhoto> getPhotosByUser(AppUser user) {
    return petPhotoRepository.findByUser(user);
  }
}
