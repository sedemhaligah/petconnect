package com.petconnect.petconnect.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.petconnect.petconnect.models.ChatMessageDTO;
import com.petconnect.petconnect.repositories.AppUserRepository;
import com.petconnect.petconnect.repositories.ChatMessageRepository;
import com.petconnect.petconnect.repositories.ChatRoomRepository;
import com.petconnect.petconnect.repositories.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class ChatWebSocketControllerTest {

  @Mock private ChatMessageRepository chatMessageRepo;
  @Mock private ChatRoomRepository chatRoomRepo;
  @Mock private AppUserRepository appUserRepository;
  @Mock private NotificationRepository notificationRepository;
  @Mock private SimpMessagingTemplate messagingTemplate;

  @InjectMocks private ChatWebSocketController chatWebSocketController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void send_shouldThrowIfPrincipalIsNull() {
    ChatMessageDTO dto = new ChatMessageDTO();
    dto.setSenderId(1);
    dto.setReceiverId(2);
    dto.setContent("test");

    assertThatThrownBy(() -> chatWebSocketController.send("room123", dto, null))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Unauthorized");
  }
}
