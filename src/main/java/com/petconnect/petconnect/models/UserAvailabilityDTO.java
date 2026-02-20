package com.petconnect.petconnect.models;

public class UserAvailabilityDTO {

  private int userId;
  private String date;

  // Getters and Setters
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
