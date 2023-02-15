package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apartmentID;
    private Long number;
    @ManyToOne
    @JoinColumn(name = "buildingID")
    private Building buildingID;

    public void setApartmentID(Long id) {
        this.apartmentID = id;
    }

    public Long getApartmentID() {
        return apartmentID;
    }

    public Building getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(Building buildingID) {
        this.buildingID = buildingID;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
