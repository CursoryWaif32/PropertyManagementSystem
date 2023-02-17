package org.example.controllers;

import org.example.dto.BuildingAddDTO;
import org.example.dto.BuildingEditDTO;
import org.example.entities.Apartment;
import org.example.entities.BuildingWithApartments;
import org.example.repositories.BuildingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buildings")
public class BuildingsController {

    final BuildingRepository buildingRepo;

    public BuildingsController(BuildingRepository buildingRepo) {
        this.buildingRepo = buildingRepo;
    }

    @GetMapping
    public Iterable<BuildingWithApartments> getAllBuildings(){
        return buildingRepo.findAll();
    }

    @GetMapping("/{id}")
    public BuildingWithApartments getBuildingById(@PathVariable Long id){
        Optional<BuildingWithApartments> building = buildingRepo.findByBuildingId(id);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Building found with ID: "+id);
        }
        return building.get();
    }

    @PostMapping
    public void addBuilding(@RequestBody BuildingAddDTO buildingDTO){
        BuildingWithApartments building = new BuildingWithApartments();
        building.setAddress(buildingDTO.address());
        List<Apartment> apartmentList = new ArrayList<>();
        if(buildingDTO.apartments().isPresent()){
            buildingDTO.apartments().get().forEach(apartmentDTO -> {
                Apartment apartment = new Apartment();
                apartment.setNumber(apartmentDTO.number());
                apartmentList.add(apartment);
            });
            building.setApartments(apartmentList);
        }
        buildingRepo.save(building);
    }

    @PatchMapping("/{id}")
    public void editBuilding(@PathVariable Long id, @RequestBody BuildingEditDTO buildingUpdate){
        BuildingWithApartments building = getBuildingById(id);
        if(buildingUpdate.address().isPresent()){
            building.setAddress(buildingUpdate.address().get());
        }
        if(buildingUpdate.addedApartments().isPresent()){
            buildingUpdate.addedApartments().get().forEach(apartmentDTO -> {
                Apartment apartment = new Apartment();
                apartment.setNumber(apartmentDTO.number());
                building.getApartments().add(apartment);
            });
        }
        buildingRepo.save(building);
    }
}
