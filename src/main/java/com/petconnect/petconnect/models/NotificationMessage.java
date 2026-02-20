package com.petconnect.petconnect.models;

public class NotificationMessage {
  private int senderId;
  private String senderName;
  private String content;

  public NotificationMessage(int senderId, String senderName, String content) {
    this.senderId = senderId;
    this.senderName = senderName;
    this.content = content;
  }

  public int getSenderId() {
    return senderId;
  }

  public String getSenderName() {
    return senderName;
  }

  public String getContent() {
    return content;
  }
}
