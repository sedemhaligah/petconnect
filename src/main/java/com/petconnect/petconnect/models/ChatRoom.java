package com.petconnect.petconnect.models;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class ChatRoom {
  @jakarta.persistence.Id @Id private String id; // e.g., UUID or composite of user IDs

  private int senderId;
  private int receiverId;

  // Constructors
  public ChatRoom() {}

  public ChatRoom(String id, int senderId, int receiverId) {
    this.id = id;
    this.senderId = senderId;
    this.receiverId = receiverId;
  }

  // Getters and Setters

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }
}
