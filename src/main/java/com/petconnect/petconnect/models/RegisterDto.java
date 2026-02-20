package com.petconnect.petconnect.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDto {

  @NotEmpty(message = "Vorname darf nicht leer sein")
  private String firstName;

  @NotEmpty(message = "Nachname darf nicht leer sein")
  private String lastName;

  @NotEmpty
  @Email(message = "Bitte geben Sie eine gültige E-Mail-Adresse ein")
  private String email;

  @Size(min = 6, message = "Das Passwort muss mindestens 6 Zeichen lang sein")
  private String password;

  private String role;
  private String phone;

  @NotEmpty(message = "Adresse darf nicht leer sein")
  private String address;

  @NotEmpty(message = "PLZ darf nicht leer sein")
  private String plz;

  private String confirmPassword;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(String plz) {
    this.plz = plz;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
