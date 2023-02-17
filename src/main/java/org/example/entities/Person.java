package org.example.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "People")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long personId;
  private String firstName;
  private String lastName;
  private String idNumber;

  @OneToMany(mappedBy = "person")
  @JsonManagedReference
  private List<Contract> contracts;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "personID")
  private List<PhoneNumber> phoneNumbers;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "personID")
  private List<Email> emails;


  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "personTypeID")
  private PersonType personType = new PersonType();


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


  public PersonType getPersonType() {
    return personType;
  }

  public void setPersonType(PersonType personTypeId) {
    this.personType = personTypeId;
  }

  public List<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public List<Email> getEmails() {
    return emails;
  }

  public void setEmails(List<Email> emails) {
    this.emails = emails;
  }

  public List<Contract> getContracts() {
    return contracts;
  }

  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }
}
