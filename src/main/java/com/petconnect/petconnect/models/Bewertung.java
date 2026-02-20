package com.petconnect.petconnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bewertungen")
public class Bewertung {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(length = 100, nullable = false)
  private String kommentar;

  private int rating; // 1-5
  private LocalDateTime erstellungsdatum;

  @ManyToOne
  @JoinColumn(name = "User_id")
  private AppUser bewerteterUser;

  @ManyToOne private AppUser autor;

  public Bewertung() {
    this.erstellungsdatum = LocalDateTime.now();
  }

  public Bewertung(String kommentar, int rating) {
    this();
    this.kommentar = kommentar;
    this.rating = rating;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKommentar() {
    return kommentar;
  }

  public void setKommentar(String kommentar) {
    this.kommentar = kommentar;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public LocalDateTime getErstellungsdatum() {
    return erstellungsdatum;
  }

  public void setErstellungsdatum(LocalDateTime erstellungsdatum) {
    this.erstellungsdatum = erstellungsdatum;
  }

  public AppUser getBewerteterUser() {
    return bewerteterUser;
  }

  public void setBewerteterUser(AppUser bewerteterUser) {
    this.bewerteterUser = bewerteterUser;
  }

  public AppUser getAutor() {
    return autor;
  }

  public void setAutor(AppUser autor) {
    this.autor = autor;
  }
}
