package org.example.controllers;

import org.example.repositories.ApartmentRepository;
import org.example.entities.Apartment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/apartments")
public class ApartmentsController {

    final ApartmentRepository apartmentRepo;

    public ApartmentsController(ApartmentRepository apartmentRepo) {
        this.apartmentRepo = apartmentRepo;
    }


    @GetMapping("/{id}")
    public Apartment getApartmentsByID(@PathVariable Long id) {
        Optional<Apartment> apartment =  apartmentRepo.findByApartmentID(id);
        if(apartment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return apartment.get();
    }

    @GetMapping
    public Iterable<Apartment> getAllApartments(){
        return apartmentRepo.findAll();
    }
}
