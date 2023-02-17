package org.example.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Apartments")
public class Apartment {
    @Id
    private Long apartmentId;
    @ManyToOne()
    @JoinColumn(name = "buildingID")
    @JsonBackReference
    private Building building;
    private Long number;

    @OneToMany(mappedBy = "apartment")
    @JsonManagedReference
    private List<Contract> contracts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return building.equals(apartment.building) && number.equals(apartment.number);
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public int hashCode() {
        return Objects.hash(building, number);
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }
}
