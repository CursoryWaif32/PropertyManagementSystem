package org.example.controllers;

import org.example.ApartmentRepository;
import org.example.entities.Apartment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    final
    ApartmentRepository apartmentRepo;

    public ApartmentController(ApartmentRepository apartmentRepo) {
        this.apartmentRepo = apartmentRepo;
    }


    @GetMapping("/{id}")
    public Optional<Apartment> getApartmentsByID(@PathVariable Long id) {
        return apartmentRepo.findByApartmentID(id);
    }

    @GetMapping
    public Iterable<Apartment> getAllApartments(){
        return apartmentRepo.findAll();
    }
}
