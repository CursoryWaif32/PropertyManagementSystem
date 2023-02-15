package org.example.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apartmentID;
    private Long number;
    public Long getNumber() {
        return number;
    }

    @ManyToOne
    @JoinColumn(name = "buildingID")
    @JsonBackReference
    private Building building;

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
