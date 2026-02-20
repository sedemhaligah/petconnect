package com.petconnect.petconnect.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.petconnect.petconnect.models.ChatMessage;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ChatMessageRepositoryTest {

  @Mock private ChatMessageRepository chatMessageRepository;

  private ChatMessage message;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    message = new ChatMessage();
    message.setId(1L);
    message.setRoomId("room123");
    message.setSenderId(1);
    message.setReceiverId(2);
    message.setContent("Hello");
    message.setTimestamp(new Date());
  }

  @Test
  void testFindByRoomIdOrderByTimestampAsc() {
    when(chatMessageRepository.findByRoomIdOrderByTimestampAsc("room123"))
        .thenReturn(List.of(message));

    List<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderByTimestampAsc("room123");

    assertThat(messages).hasSize(1);
    assertThat(messages.get(0).getRoomId()).isEqualTo("room123");

    verify(chatMessageRepository).findByRoomIdOrderByTimestampAsc("room123");
  }

  @Test
  void testFindAllBySenderIdOrReceiverIdOrderByTimestampDesc() {
    when(chatMessageRepository.findAllBySenderIdOrReceiverIdOrderByTimestampDesc(1, 1))
        .thenReturn(List.of(message));

    List<ChatMessage> messages =
        chatMessageRepository.findAllBySenderIdOrReceiverIdOrderByTimestampDesc(1, 1);

    assertThat(messages).hasSize(1);
    assertThat(messages.get(0).getSenderId()).isEqualTo(1);

    verify(chatMessageRepository).findAllBySenderIdOrReceiverIdOrderByTimestampDesc(1, 1);
  }
}
