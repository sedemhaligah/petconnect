package com.petconnect.petconnect.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_availability")
public class UserAvailability {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private AppUser user;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDate createdAt;

  @Column(name = "updated_at")
  private LocalDate updatedAt;

  // Constructors, Getters, and Setters
  public UserAvailability() {}

  public UserAvailability(AppUser user, LocalDate date) {
    this.user = user;
    this.date = date;
    this.createdAt = LocalDate.now();
    this.updatedAt = LocalDate.now();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public AppUser getUser() {
    return user;
  }

  public void setUser(AppUser user) {
    this.user = user;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }
}
