package com.petconnect.petconnect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;
  private double price;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ElementCollection
  @CollectionTable(name = "service_features", joinColumns = @JoinColumn(name = "service_id"))
  @Column(name = "feature")
  private List<String> features = new ArrayList<>();

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getFeatures() {
    return features;
  }

  public void setFeatures(List<String> features) {
    this.features = features;
  }

  public void addFeature(String feature) {
    this.features.add(feature);
  }
}
