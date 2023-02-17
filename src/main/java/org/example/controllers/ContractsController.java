package org.example.controllers;

import org.example.dto.ContractDTO;
import org.example.dto.ContractEditDTO;
import org.example.entities.BuildingWithApartments;
import org.example.entities.Contract;
import org.example.entities.Person;
import org.example.repositories.BuildingRepository;
import org.example.repositories.ContractRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/contracts")
public class ContractsController {

    final ContractRepository contractRepo;
    final PeopleRepository peopleRepo;
    final BuildingRepository buildingRepo;


    public ContractsController(ContractRepository contractRepo, PeopleRepository peopleRepo, BuildingRepository buildingRepo) {
        this.contractRepo = contractRepo;
        this.peopleRepo = peopleRepo;
        this.buildingRepo = buildingRepo;
    }

    @GetMapping
    public Iterable<Contract> getAllContracts(@RequestParam Optional<String> type){
        if(type.isEmpty()){
            return contractRepo.findAll();
        }
        return contractRepo.findContractByContractTypeNameLikeIgnoreCase(type.get());
    }

    @GetMapping("/{id}")
    public Contract getContractByID(@PathVariable Long id){
        Optional<Contract> contract = contractRepo.findByContractId(id);
        if(contract.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Contract found with ID: "+id);
        }
        return contract.get();
    }

    @PostMapping
    public void addContract(@RequestBody ContractDTO contractDTO){
        Long personID = contractDTO.personID();
        Person person = peopleRepo.findByPersonId(personID).orElse(null);
        if(person == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Person found with personID: "+personID);
        }
        if(person.getIdNumber() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Person does not have necessary details to create a contract (No ID number)");
        }
        Long buildingID = contractDTO.BuildingID();
        BuildingWithApartments building = buildingRepo.findByBuildingId(buildingID).orElse(null);
        if(building == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Building found with ID: "+buildingID);
        }
        Long apartmentNumber = contractDTO.ApartmentNumber();
        if(building.getApartments().stream().noneMatch(apartment -> apartment.getNumber().equals(apartmentNumber))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No apartment found with Number: "+ apartmentNumber +" for Building: "+buildingID);
        }
        Long contractType = contractDTO.ContractTypeID();
        contractRepo.uspCreateContract(personID,apartmentNumber,buildingID,contractType,null);
    }

    @DeleteMapping("/{id}")
    public void endContract(@PathVariable Long id){
        Contract contract = getContractByID(id);
        if(contract.getContractEndDate()!=null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contract already finished");
        }
        contractRepo.uspEndContract(contract.getContractId());
    }

    @PatchMapping("/{id}")
    public void editContract(@PathVariable Long id, @RequestBody ContractEditDTO contractEditDTO){
        Contract contract = getContractByID(id);
        if(contract.getContractEndDate()!=null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Cannot edit a finished contract");
        }
        Long currentBuildingID = contract.getApartment().getBuilding().getBuildingId();
        Long newBuildingID = contractEditDTO.buildingID().orElse(currentBuildingID);
        Long currentApartmentNumber = contract.getApartment().getNumber();
        Long newApartmentNumber = contractEditDTO.apartmentNumber();
        if(currentBuildingID.equals(newBuildingID) && currentApartmentNumber.equals(newApartmentNumber)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Difference in apartmentNumber or buildingID");
        }
        contractRepo.uspContractChangeApartment(id,newApartmentNumber,newBuildingID);

    }
}
