package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApartmentTrafficLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long apartmentEntranceLogId;
  private long personId;
  private long apartmentId;
  private java.sql.Timestamp timeOfEntrance;
  private java.sql.Timestamp timeOfExit;


  public long getApartmentEntranceLogId() {
    return apartmentEntranceLogId;
  }

  public void setApartmentEntranceLogId(long apartmentEntranceLogId) {
    this.apartmentEntranceLogId = apartmentEntranceLogId;
  }


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }


  public long getApartmentId() {
    return apartmentId;
  }

  public void setApartmentId(long apartmentId) {
    this.apartmentId = apartmentId;
  }


  public java.sql.Timestamp getTimeOfEntrance() {
    return timeOfEntrance;
  }

  public void setTimeOfEntrance(java.sql.Timestamp timeOfEntrance) {
    this.timeOfEntrance = timeOfEntrance;
  }


  public java.sql.Timestamp getTimeOfExit() {
    return timeOfExit;
  }

  public void setTimeOfExit(java.sql.Timestamp timeOfExit) {
    this.timeOfExit = timeOfExit;
  }

}
