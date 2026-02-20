package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.ChatMessage;
import com.petconnect.petconnect.models.ChatMessageDTO;
import com.petconnect.petconnect.models.NotificationMessage;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.ChatMessageRepository;
import com.petconnect.petconnect.repositories.ChatRoomRepository;
import com.petconnect.petconnect.repositories.NotificationRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

  @Autowired private ChatMessageRepository chatMessageRepo;

  @Autowired private ChatRoomRepository chatRoomRepo;

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private final SimpMessagingTemplate messagingTemplate;

  public ChatWebSocketController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping("/chat.send.{roomId}")
  @SendTo("/topic/messages/{roomId}")
  public ChatMessage send(
      @DestinationVariable String roomId, @Payload ChatMessageDTO messageDTO, Principal principal) {
    if (principal == null || principal.getName() == null) {
      throw new RuntimeException("Unauthorized user cannot send chat messages.");
    }

    System.out.println("Authenticated user: " + principal.getName());

    ChatMessage message =
        new ChatMessage(
            roomId,
            messageDTO.getSenderId(),
            messageDTO.getReceiverId(), // ✅ ensure this is passed from frontend
            messageDTO.getContent());

    chatMessageRepo.save(message);

    // Send a notification directly to the receiver (private queue)
    String receiverUsername =
        appUserRepository
            .findById(message.getReceiverId())
            .map(user -> user.getEmail()) // ✅ This must match Principal.getName()
            .orElseThrow(() -> new RuntimeException("Receiver not found"));

    String senderName =
        appUserRepository
            .findById(message.getSenderId())
            .map(user -> user.getFirstName() + " " + user.getLastName())
            .orElse("Unbekannt");

    NotificationMessage notification =
        new NotificationMessage(message.getSenderId(), senderName, message.getContent());

    messagingTemplate.convertAndSendToUser(receiverUsername, "/queue/notify", notification);

    return message;
  }
}
