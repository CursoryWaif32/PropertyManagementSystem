package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhoneNumbers")
public class PhoneNumber {

  @Id
  private String phoneNumber;


  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PhoneNumber that = (PhoneNumber) o;
    return phoneNumber.equals(that.phoneNumber);
  }

  @Override
  public int hashCode() {
    return phoneNumber.hashCode();
  }
}
