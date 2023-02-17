package org.example.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Buildings")
public class Building {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long buildingId;
  private String address;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "building")
  @JsonManagedReference
  private List<Apartment> apartments;


  public long getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(long buildingId) {
    this.buildingId = buildingId;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<Apartment> getApartments() {
    return apartments;
  }

  public void setApartments(List<Apartment> apartments) {
    this.apartments = apartments;
  }
}
