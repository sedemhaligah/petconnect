package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.AppUser;
import com.petconnect.petconnect.models.Notification;
import com.petconnect.petconnect.models.NotificationMessage;
import com.petconnect.petconnect.repositories.NotificationRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired private NotificationRepository notificationRepository;

  @GetMapping("/unread")
  public List<NotificationMessage> getUnread(@AuthenticationPrincipal AppUser user) {
    return notificationRepository
        .findByReceiverIdAndIsReadFalseOrderByTimestampDesc(user.getId())
        .stream()
        .map(n -> new NotificationMessage(n.getSenderId(), n.getSenderName(), n.getContent()))
        .collect(Collectors.toList());
  }

  @PostMapping("/mark-read")
  public void markAsRead(@AuthenticationPrincipal AppUser user, @RequestParam int senderId) {

    List<Notification> unread =
        notificationRepository.findByReceiverIdAndSenderIdAndIsReadFalse(user.getId(), senderId);

    unread.forEach(n -> n.setRead(true));
    notificationRepository.saveAll(unread);
  }
}
