package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);

  List<ChatMessage> findAllBySenderIdOrReceiverIdOrderByTimestampDesc(int senderId, int receiverId);
}
