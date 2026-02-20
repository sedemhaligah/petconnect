package com.petconnect.petconnect.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int receiverId;

  private String roomId;
  private int senderId;
  private String content;
  private Date timestamp = new Date();

  // Constructors
  public ChatMessage() {}

  public ChatMessage(String roomId, int senderId, int receiverId, String content) {
    this.roomId = roomId;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.content = content;
    this.timestamp = new Date();
  }

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }
}
