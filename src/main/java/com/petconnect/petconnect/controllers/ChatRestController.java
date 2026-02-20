package com.petconnect.petconnect.controllers;

import com.petconnect.petconnect.models.ChatMessage;
import com.petconnect.petconnect.models.ChatRoom;
import com.petconnect.petconnect.repositories.ChatMessageRepository;
import com.petconnect.petconnect.repositories.ChatRoomRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

  @Autowired private ChatRoomRepository chatRoomRepo;
  @Autowired private ChatMessageRepository chatMessageRepo;

  // Get or create chat room
  @PostMapping("/room")
  public String getOrCreateRoom(@RequestParam int senderId, @RequestParam int receiverId) {
    return chatRoomRepo
        .findBySenderIdAndReceiverId(senderId, receiverId)
        .orElseGet(
            () -> {
              String roomId = UUID.randomUUID().toString();
              ChatRoom room = new ChatRoom(roomId, senderId, receiverId);
              chatRoomRepo.save(room);
              return room;
            })
        .getId();
  }

  // Get chat history
  @GetMapping("/history/{roomId}")
  public List<ChatMessage> getChatHistory(@PathVariable String roomId) {
    return chatMessageRepo.findByRoomIdOrderByTimestampAsc(roomId);
  }
}
