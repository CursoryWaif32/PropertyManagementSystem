package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "People")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long personId;
  private String firstName;
  private String lastName;
  private String idNumber;

  @ManyToOne
  private PersonType personTypeId;


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }


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


  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }


  public PersonType getPersonTypeId() {
    return personTypeId;
  }

  public void setPersonTypeId(PersonType personTypeId) {
    this.personTypeId = personTypeId;
  }

}
