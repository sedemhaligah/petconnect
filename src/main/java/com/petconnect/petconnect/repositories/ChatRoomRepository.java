package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
  Optional<ChatRoom> findBySenderIdAndReceiverId(int senderId, int receiverId);

  List<ChatRoom> findBySenderIdOrReceiverId(int userId1, int userId2);
}
