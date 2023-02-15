package org.example;

import org.example.entities.Apartment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Controller {

    final
    ApartmentRepository apartmentRepo;

    public Controller(ApartmentRepository apartmentRepo) {
        this.apartmentRepo = apartmentRepo;
    }


    @GetMapping("/apartments/{id}")
    public Optional<Apartment> getApartmentsByID(@PathVariable Long id) {
        return apartmentRepo.findByApartmentID(id);
    }

    @GetMapping("/apartments")
    public Iterable<Apartment> getAllApartments(){
        return apartmentRepo.findAll();
    }
}
