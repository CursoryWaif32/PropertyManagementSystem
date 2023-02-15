package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apartmentID;
    private Long number;

    public void setApartmentID(Long id) {
        this.apartmentID = id;
    }

    public Long getApartmentID() {
        return apartmentID;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
