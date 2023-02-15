package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Emails")
public class Email {

  @Id
  private String email;


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
