package org.example.entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Apartments")
@IdClass(Apartment.class)
public class Apartment implements Serializable {
    @Id
    private Long buildingID;
    @Id
    private Long number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return buildingID.equals(apartment.buildingID) && number.equals(apartment.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingID, number);
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
