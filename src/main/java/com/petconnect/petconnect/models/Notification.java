package com.petconnect.petconnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int senderId;
  private int receiverId;
  private String senderName;
  private String content;
  private boolean isRead;

  @Column(nullable = false)
  private LocalDateTime timestamp = LocalDateTime.now();

  public Notification() {}

  public Notification(
      int senderId,
      int receiverId,
      String senderName,
      String content,
      boolean isRead,
      LocalDateTime timestamp) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.senderName = senderName;
    this.content = content;
    this.isRead = isRead;
    this.timestamp = LocalDateTime.now();
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  // Getters and setters...

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean read) {
    isRead = read;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public int getSenderId() {
    return senderId;
  }

  public int getReceiverId() {
    return receiverId;
  }

  public String getSenderName() {
    return senderName;
  }

  public String getContent() {
    return content;
  }
}
