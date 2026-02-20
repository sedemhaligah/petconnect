package com.petconnect.petconnect.repositories;

import com.petconnect.petconnect.models.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByReceiverIdAndIsReadFalseOrderByTimestampDesc(int receiverId);

  List<Notification> findByReceiverIdAndSenderIdAndIsReadFalse(int receiverId, int senderId);
}
