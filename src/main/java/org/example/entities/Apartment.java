package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apartmentID;
    private Long number;
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
