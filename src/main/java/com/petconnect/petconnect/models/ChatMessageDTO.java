package com.petconnect.petconnect.models;

public class ChatMessageDTO {
  private String roomId;
  private int senderId;
  private int receiverId;
  private String content;

  // Getters and setters
  public ChatMessageDTO() {} // required for Jackson

  public ChatMessageDTO(int senderId, int receiverId, String content) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.content = content;
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

  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
