package org.example.controllers;

import org.example.entities.Apartment;
import org.example.entities.Building;
import org.example.repositories.ApartmentRepository;
import org.example.repositories.BuildingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buildings")
public class BuildingsController {

    final BuildingRepository buildingRepo;
    final ApartmentRepository apartmentRepo;

    public BuildingsController(BuildingRepository buildingRepo, ApartmentRepository apartmentRepo) {
        this.buildingRepo = buildingRepo;
        this.apartmentRepo = apartmentRepo;
    }

    @GetMapping
    public Iterable<Building> getAllBuildings(){
        return buildingRepo.findAll();
    }

    @GetMapping("/{id}")
    public Building getBuildingById(@PathVariable Long id){
        Optional<Building> building = buildingRepo.findByBuildingId(id);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return building.get();
    }

    @GetMapping("/{id}/apartments")
    public List<Apartment> getApartmentsForBuilding(@PathVariable Long id){

        Building buildingID = getBuildingById(id);
        return buildingID.getApartments();
    }

}