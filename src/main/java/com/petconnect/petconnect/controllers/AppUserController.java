package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.*;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.BewertungRepository;
import com.petconnect.petconnect.repositories.ChatMessageRepository;
import com.petconnect.petconnect.repositories.PetPhotoRepository;
import com.petconnect.petconnect.services.AppUserService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppUserController {

  @Autowired private AppUserRepository repo;
  @Autowired private ChatMessageRepository chatMessageRepo;
  @Autowired private AppUserService appUserService;
  @Autowired private BewertungRepository bewertungRepository;
  @Autowired private PetPhotoRepository petPhotoRepository;

  @GetMapping("/register")
  public String register(Model model) {
    RegisterDto registerDto = new RegisterDto();
    model.addAttribute(registerDto);
    model.addAttribute("success", false);
    return "anmelden";
  }

  @PostMapping("/register")
  public String register(
      Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {

    // model allows us to send data to the page
    // registerDto allows us to retrieve data from the form
    // result allows us to display errors (available in object result)

    // check if passwords match
    if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
      result.addError(new FieldError("registerDto", "confirmPassword", "Passwords do not match"));
    }

    AppUser appUser = repo.findByEmail(registerDto.getEmail());

    if (appUser != null) {
      result.addError(new FieldError("registerDto", "email", "Email already exists"));
    }

    if (result.hasErrors()) {
      return "anmelden";
    }

    try {
      // create new account
      var bCryptPasswordEncoder = new BCryptPasswordEncoder();

      AppUser newUser = new AppUser();
      newUser.setFirstName(registerDto.getFirstName());
      newUser.setLastName(registerDto.getLastName());
      newUser.setEmail(registerDto.getEmail());
      newUser.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
      newUser.setRole(registerDto.getRole()); // Set role from the form
      newUser.setPhone(registerDto.getPhone());
      newUser.setAddress(registerDto.getAddress());
      newUser.setPlz(registerDto.getPlz());
      newUser.setCreatedAt(new java.util.Date());

      repo.save(newUser);

      model.addAttribute("registerDto", new RegisterDto());
      model.addAttribute("success", true);
    } catch (Exception e) {

    }

    return "login";
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

  @GetMapping("/profile")
  public String showProfilePage(Model model) {
    // Get current user
    String username = null;
    try {
      username = SecurityContextHolder.getContext().getAuthentication().getName();
    } catch (Exception e) {
      // Log or handle the exception if authentication context is not found
      return "redirect:/login";
    }

    if (username == null) {
      return "redirect:/login"; // Redirect if the username is still null
    }

    AppUser appUser = appUserService.getUserByEmail(username);

    if (appUser != null) {
      model.addAttribute("user", appUser);
      model.addAttribute("isOwnProfile", true);
      model.addAttribute("petPhotos", petPhotoRepository.findByUser(appUser));
      model.addAttribute("currentUser", appUser);

      return "Profilseite";
    } else {
      return "redirect:/login";
    }
  }

  // View another user's profile
  @GetMapping("/profile/{id}")
  public String viewUserProfile(@PathVariable int id, Model model) {
    AppUser appUser = appUserService.getUserById(id);

    if (appUser != null) {
      model.addAttribute("user", appUser);
      model.addAttribute("petPhotos", petPhotoRepository.findByUser(appUser));

      List<Bewertung> bewertungen =
          bewertungRepository.findByBewerteterUserOrderByErstellungsdatumDesc(appUser);
      model.addAttribute("bewertungen", bewertungen);

      List<AppUser> ähnlicheNutzer =
          repo.findByPlzAndRoleAndIdNot(appUser.getPlz(), appUser.getRole(), appUser.getId());
      model.addAttribute("ähnlicheNutzer", ähnlicheNutzer);

      // Get the currently authenticated user
      String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
      AppUser currentUser = appUserService.getUserByEmail(currentUsername);

      // Add current user to model for navbar display
      model.addAttribute("currentUser", currentUser);

      // Determine if this is their own profile
      boolean isOwnProfile = currentUser != null && currentUser.getId() == appUser.getId();
      model.addAttribute("isOwnProfile", isOwnProfile);

      return "Profilseite";
    } else {
      return "redirect:/error";
    }
  }

  // Show profile edit page
  @GetMapping("/profile/edit")
  public String showEditProfilePage(Model model) {
    // Get current user
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser appUser = appUserService.getUserByEmail(username);

    if (appUser != null) {
      model.addAttribute("user", appUser);
      model.addAttribute("petPhotos", petPhotoRepository.findByUser(appUser));
      return "Profilbearbeitung";
    } else {
      return "redirect:/login";
    }
  }

  @GetMapping("/profile/image/{id}")
  @ResponseBody
  public ResponseEntity<byte[]> getProfileImage(@PathVariable int id) {
    AppUser user = appUserService.getUserById(id);

    if (user != null && user.getProfilePicURL() != null) {
      try {
        Path imagePath =
            Paths.get(
                "." + user.getProfilePicURL()); // assumes image path starts with "/uploads/..."
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String contentType = Files.probeContentType(imagePath);

        return ResponseEntity.ok()
            .header("Content-Type", contentType != null ? contentType : "image/jpeg")
            .body(imageBytes);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return ResponseEntity.notFound().build();
  }

  // Update user profile
  @PostMapping("/profile/update")
  public String updateProfile(
      @ModelAttribute AppUser updatedUser, // AppUser object to handle updates
      @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
      @RequestParam(value = "aboutMe", required = false) String aboutMe,
      @RequestParam(value = "experience", required = false) String experience,
      RedirectAttributes redirectAttributes) {

    // Get current user
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser = appUserService.getUserByEmail(username);

    if (currentUser != null) {
      // Update the 'About Me' section independently
      if (aboutMe != null) {
        currentUser.setAboutMe(aboutMe); // Only update aboutMe
      }

      // If updatedUser has new details, update them while keeping existing ones intact
      if (updatedUser.getFirstName() != null) {
        currentUser.setFirstName(updatedUser.getFirstName());
      }
      if (updatedUser.getLastName() != null) {
        currentUser.setLastName(updatedUser.getLastName());
      }
      if (updatedUser.getPhone() != null) {
        currentUser.setPhone(updatedUser.getPhone());
      }
      if (updatedUser.getAddress() != null) {
        currentUser.setAddress(updatedUser.getAddress());
      }
      if (updatedUser.getPlz() != null) {
        currentUser.setPlz(updatedUser.getPlz());
      }

      // Set experience if provided
      if (experience != null && !experience.isEmpty()) {
        currentUser.setExperience(experience);
      }

      // Handle profile image upload if necessary
      if (profileImage != null && !profileImage.isEmpty()) {
        try {
          // Ensure the 'uploads' directory exists
          Path uploadDir = Paths.get("uploads");
          if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
          }

          // Delete old image if exists
          String oldPic = currentUser.getProfilePicURL();
          if (oldPic != null && oldPic.startsWith("/uploads/")) {
            Path oldPicPath = Paths.get("." + oldPic);
            Files.deleteIfExists(oldPicPath);
          }

          // Create unique filename
          String fileName = UUID.randomUUID().toString() + "-" + profileImage.getOriginalFilename();
          Path filePath = uploadDir.resolve(fileName);

          // Save new image
          Files.write(filePath, profileImage.getBytes());

          // Update profile pic URL
          currentUser.setProfilePicURL("/uploads/" + fileName);
        } catch (IOException e) {
          e.printStackTrace();
          redirectAttributes.addFlashAttribute("error", "Profile image upload failed.");
        }
      }

      // Save updated user
      repo.save(currentUser);

      redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
      return "redirect:/profile";
    }

    return "redirect:/login";
  }

  // Add service to profile
  @PostMapping("/profile/service/add")
  public String addService(
      @RequestParam String title,
      @RequestParam double price,
      @RequestParam String description,
      @RequestParam(value = "features", required = false) String features,
      RedirectAttributes redirectAttributes) {

    // Get current user
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser = appUserService.getUserByEmail(username);

    if (currentUser != null) {
      // Create new service
      Service service = new Service();
      service.setTitle(title);
      service.setPrice(price);
      service.setDescription(description);

      // Parse features (comma-separated) and add to service
      if (features != null && !features.isEmpty()) {
        List<String> featureList = Arrays.asList(features.split(","));
        for (String feature : featureList) {
          service.addFeature(feature.trim());
        }
      }

      // Add service to user
      currentUser.addService(service);

      // Save user
      repo.save(currentUser);

      redirectAttributes.addFlashAttribute("success", "Service added successfully!");
    }

    return "redirect:/profile/edit";
  }

  // Remove service from profile
  @PostMapping("/profile/service/remove")
  public String removeService(@RequestParam int serviceId, RedirectAttributes redirectAttributes) {

    // Get current user
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser = appUserService.getUserByEmail(username);

    if (currentUser != null) {
      // Remove service from user
      currentUser.removeService(serviceId);

      // Save user
      repo.save(currentUser);

      redirectAttributes.addFlashAttribute("success", "Service removed successfully!");
    }

    return "redirect:/profile/edit";
  }

  @PostMapping("/profile/service/update")
  public String updateService(
      @RequestParam int serviceId,
      @RequestParam String title,
      @RequestParam double price,
      @RequestParam String description,
      @RequestParam(value = "features", required = false) List<String> features,
      RedirectAttributes redirectAttributes) {

    // Get current user
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser = appUserService.getUserByEmail(username);

    if (currentUser != null) {
      // Find the service to update
      Service service =
          currentUser.getServices().stream()
              .filter(s -> s.getId() == serviceId)
              .findFirst()
              .orElse(null);

      if (service != null) {
        // Update fields
        service.setTitle(title);
        service.setPrice(price);
        service.setDescription(description);

        // Replace features
        service.getFeatures().clear();
        if (features != null) {
          for (String feature : features) {
            if (!feature.trim().isEmpty()) {
              service.addFeature(feature.trim());
            }
          }
        }

        // Save the updated user with the updated service
        repo.save(currentUser);

        redirectAttributes.addFlashAttribute("success", "Service aktualisiert!");
      }
    }

    return "redirect:/profile/edit";
  }

  @GetMapping("/betreuerfinden")
  public String showBetreuerFindenPage(
      @RequestParam(required = false) String plz,
      @RequestParam(defaultValue = "BETREUER") String role,
      Model model) {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser;
    if (email != null && !email.equals("anonymousUser")) {
      currentUser = appUserService.getUserByEmail(email);
      model.addAttribute("user", currentUser);
    } else {
      currentUser = null;
    }

    if (plz != null && !plz.isEmpty()) {
      String roleLower = role.toLowerCase();
      List<AppUser> userList = repo.findByRoleIgnoreCaseAndPlz(roleLower, plz);

      if (currentUser != null) {
        userList.removeIf(u -> u.getId() == currentUser.getId());
      }

      model.addAttribute("betreuerList", userList);
      model.addAttribute("plz", plz);
      model.addAttribute("role", roleLower);
    }

    return "betreuerfinden";
  }

  @GetMapping("/api/betreuer")
  @ResponseBody
  public List<AppUser> getCaregiversByPlz(@RequestParam String plz) {
    return repo.findByRoleIgnoreCaseAndPlz("betreuer", plz);
  }

  @GetMapping("/chat")
  public String showChatPage(@RequestParam("receiverId") int receiverId, Model model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser = appUserService.getUserByEmail(username);

    if (currentUser != null) {
      AppUser receiver = appUserService.getUserById(receiverId);
      if (receiver == null) {
        return "redirect:/error"; // or show a message
      }

      model.addAttribute("user", currentUser);
      model.addAttribute("receiverId", receiverId);
      model.addAttribute(
          "receiverName", receiver.getFirstName() + " " + receiver.getLastName()); // ✅ Add this
      return "chat";
    }

    return "redirect:/login";
  }

  @PostMapping("/profile/uploadPetPhotos")
  public String uploadPetPhotos(
      @RequestParam("petPhotos") List<MultipartFile> petPhotos,
      RedirectAttributes redirectAttributes) {

    // Get current user from security context
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    AppUser currentUser = appUserService.getUserByEmail(username);

    if (currentUser == null) {
      return "redirect:/login";
    }

    Path uploadDir = Paths.get("uploads");

    try {
      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }

      // Optional: Limit max 4 pet photos
      int maxPhotos = 4;
      int count = 0;

      for (MultipartFile file : petPhotos) {
        if (file != null && !file.isEmpty() && count < maxPhotos) {
          // Save file with unique filename
          String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
          Path filePath = uploadDir.resolve(fileName);
          Files.write(filePath, file.getBytes());

          // Create new PetPhoto entity
          PetPhoto petPhoto = new PetPhoto();
          petPhoto.setFileName(fileName);
          petPhoto.setUser(currentUser); // link to current user

          // Save pet photo entity in DB
          petPhotoRepository.save(petPhoto);

          count++;
        }
      }

      redirectAttributes.addFlashAttribute("success", "Haustierfotos erfolgreich hochgeladen!");

    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("error", "Fehler beim Hochladen der Haustierfotos.");
    }

    return "redirect:/profile";
  }

  @GetMapping("/chat-overview")
  public String getChatOverview(Model model, Principal principal) {
    AppUser currentUser = repo.findByEmail(principal.getName());

    List<ChatMessage> allMessages =
        chatMessageRepo.findAllBySenderIdOrReceiverIdOrderByTimestampDesc(
            currentUser.getId(), currentUser.getId());

    Map<Long, ChatOverviewEntry> conversations = new LinkedHashMap<>();

    for (ChatMessage msg : allMessages) {
      int otherUserId =
          msg.getSenderId() == currentUser.getId() ? msg.getReceiverId() : msg.getSenderId();

      if (!conversations.containsKey((long) otherUserId)) {
        AppUser user = repo.findById((int) otherUserId).orElse(null);
        if (user != null) {
          conversations.put(
              (long) otherUserId,
              new ChatOverviewEntry(user, msg.getContent(), msg.getTimestamp()));
        }
      }
    }

    model.addAttribute("chatUsers", conversations.values());
    return "chats";
  }

  @GetMapping("/contact")
  public String showContactPage(Model model) {
    return "Contact";
  }
}
