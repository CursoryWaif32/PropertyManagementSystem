package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Apartments")
public class ApartmentWithBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apartmentID;
    private Long number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buildingID")
    private Building building;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Long getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(Long apartmentID) {
        this.apartmentID = apartmentID;
    }
}
