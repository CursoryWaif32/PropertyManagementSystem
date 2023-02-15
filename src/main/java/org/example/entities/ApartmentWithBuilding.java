package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Apartments")
public class ApartmentWithBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apartmentID;
    private Long number;

    @ManyToOne
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
}
