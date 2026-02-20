package com.petconnect.petconnect.models;

import java.util.Date;

public class ChatOverviewEntry {
  private AppUser user;
  private String lastMessage;
  private Date timestamp;

  public ChatOverviewEntry(AppUser user, String lastMessage, Date timestamp) {
    this.user = user;
    this.lastMessage = lastMessage;
    this.timestamp = new Date();
  }

  // constructor, getters, setters

  public AppUser getUser() {
    return user;
  }

  public void setUser(AppUser user) {
    this.user = user;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
