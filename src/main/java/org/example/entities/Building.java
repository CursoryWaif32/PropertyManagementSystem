package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Buildings")
public class Building {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long buildingId;
  private String address;


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

}
