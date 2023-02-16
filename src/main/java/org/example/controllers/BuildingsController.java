package org.example.controllers;

import org.example.dto.BuildingDTO;
import org.example.entities.BuildingWithApartments;
import org.example.repositories.ApartmentRepository;
import org.example.repositories.BuildingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public Iterable<BuildingWithApartments> getAllBuildings(){
        return buildingRepo.findAll();
    }

    @GetMapping("/{id}")
    public BuildingWithApartments getBuildingById(@PathVariable Long id){
        Optional<BuildingWithApartments> building = buildingRepo.findByBuildingId(id);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return building.get();
    }

    @PostMapping
    public void addBuilding(@RequestBody BuildingWithApartments building){
        buildingRepo.save(building);
    }

    @PatchMapping("/{id}")
    public void editBuilding(@PathVariable Long id, @RequestBody Optional<BuildingDTO> buildingUpdate){
        BuildingWithApartments building = getBuildingById(id);
        if(buildingUpdate.isPresent()){
            if(buildingUpdate.get().address != null){
                building.setAddress(buildingUpdate.get().address);
            }
        }
        buildingRepo.save(building);
    }
}
