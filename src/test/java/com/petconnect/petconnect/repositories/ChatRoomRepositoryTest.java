package com.petconnect.petconnect.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.petconnect.petconnect.models.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ChatRoomRepositoryTest {

  @Mock private ChatRoomRepository chatRoomRepository;

  private ChatRoom mockChatRoom;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    mockChatRoom = new ChatRoom();
    mockChatRoom.setId("room123");
    mockChatRoom.setSenderId(1);
    mockChatRoom.setReceiverId(2);
  }

  @Test
  void testFindBySenderIdAndReceiverId() {
    when(chatRoomRepository.findBySenderIdAndReceiverId(1, 2))
        .thenReturn(Optional.of(mockChatRoom));

    Optional<ChatRoom> result = chatRoomRepository.findBySenderIdAndReceiverId(1, 2);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo("room123");

    verify(chatRoomRepository).findBySenderIdAndReceiverId(1, 2);
  }

  @Test
  void testFindBySenderIdOrReceiverId() {
    when(chatRoomRepository.findBySenderIdOrReceiverId(1, 1)).thenReturn(List.of(mockChatRoom));

    List<ChatRoom> result = chatRoomRepository.findBySenderIdOrReceiverId(1, 1);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getSenderId()).isEqualTo(1);

    verify(chatRoomRepository).findBySenderIdOrReceiverId(1, 1);
  }
}
