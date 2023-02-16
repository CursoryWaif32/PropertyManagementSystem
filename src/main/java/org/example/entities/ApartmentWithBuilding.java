package org.example.entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Apartments")
@IdClass(ApartmentWithBuilding.class)
public class ApartmentWithBuilding implements Serializable {
    @Id
    private Long number;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buildingID")
    private Building building;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentWithBuilding that = (ApartmentWithBuilding) o;
        return number.equals(that.number) && building.equals(that.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, building);
    }

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
