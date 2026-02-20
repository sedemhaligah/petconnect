package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.PetPhoto;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.PetPhotoRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class PetPhotoController {

  @Autowired private PetPhotoRepository petPhotoRepository;
  @Autowired private AppUserRepository appUserRepository;

  private final Path uploadDir = Paths.get("uploads/pet_photos");

  @PostMapping("/upload-pet-photos")
  public String uploadPetPhotos(
      @RequestParam("petPhotos") MultipartFile[] files,
      @AuthenticationPrincipal AppUser user,
      RedirectAttributes redirectAttributes)
      throws IOException {

    List<PetPhoto> existing = petPhotoRepository.findByUser(user);
    if (existing.size() + files.length > 3) {
      redirectAttributes.addFlashAttribute("error", "Maximal 3 Tierfotos erlaubt.");
      return "redirect:/profile";
    }

    for (MultipartFile file : files) {
      if (!file.isEmpty()) {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Files.copy(
            file.getInputStream(),
            uploadDir.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
        PetPhoto photo = new PetPhoto();
        photo.setFileName(filename);
        photo.setUser(user);
        petPhotoRepository.save(photo);
      }
    }

    redirectAttributes.addFlashAttribute("success", "Fotos erfolgreich hochgeladen.");
    return "redirect:/profile";
  }

  @PostMapping("/delete-photo/{id}")
  public String deletePhoto(
      @PathVariable Long id,
      @AuthenticationPrincipal(expression = "username") String email,
      RedirectAttributes redirectAttributes)
      throws IOException {

    AppUser user = appUserRepository.findByEmail(email);
    if (user == null) {
      redirectAttributes.addFlashAttribute("error", "Benutzer nicht gefunden.");
      return "redirect:/profile";
    }

    PetPhoto photo = petPhotoRepository.findById(id).orElse(null);
    if (photo == null
        || photo.getFileName() == null
        || photo.getUser() == null
        || photo.getUser().getId() != (user.getId())) {
      redirectAttributes.addFlashAttribute("error", "Ungültiger Fotozugriff oder fehlende Datei.");
      return "redirect:/profile";
    }

    Files.deleteIfExists(uploadDir.resolve(photo.getFileName()));
    petPhotoRepository.delete(photo);
    redirectAttributes.addFlashAttribute("success", "Foto gelöscht.");

    return "redirect:/profile";
  }
}
