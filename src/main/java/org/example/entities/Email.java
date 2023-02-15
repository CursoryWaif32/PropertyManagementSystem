package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Emails")
public class Email {

  private long personId;
  @Id
  private String email;


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
