package org.example.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.dto.BuildingAddDTO;
import org.example.dto.BuildingEditDTO;
import org.example.dto.ContractDTO;
import org.example.dto.ContractEditDTO;
import org.example.entities.Apartment;
import org.example.entities.Building;
import org.example.entities.Contract;
import org.example.entities.Person;
import org.example.repositories.BuildingRepository;
import org.example.repositories.ContractRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buildings")
public class BuildingsController {

    final BuildingRepository buildingRepo;
    final ContractRepository contractRepo;
    final PeopleRepository peopleRepo;

    public BuildingsController(BuildingRepository buildingRepo, ContractRepository contractRepo, PeopleRepository peopleRepo) {
        this.buildingRepo = buildingRepo;
        this.contractRepo = contractRepo;
        this.peopleRepo = peopleRepo;
    }

    @GetMapping()
    public Iterable<Building> getAllBuildings(){
        return buildingRepo.findAll();
    }


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="404",description = "Unknown Building"),
                    @ApiResponse(responseCode = "200", description="OK")
            }
    )
    @GetMapping("/{buildingID}")
    public Building getBuildingById(@PathVariable Long buildingID){
        Optional<Building> building = buildingRepo.findByBuildingId(buildingID);
        if(building.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unknown newBuildingID: "+buildingID);
        }
        return building.get();
    }

    @PostMapping
    public void addBuilding(@RequestBody BuildingAddDTO buildingDTO){
        Building building = new Building();
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

    @PatchMapping("/{buildingID}")
    public void editBuilding(@PathVariable Long buildingID, @RequestBody BuildingEditDTO buildingUpdate){
        Building building = getBuildingById(buildingID);
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

    @GetMapping("/{buildingID}/apartments/{apartmentID}")
    public Apartment getApartmentByBuilding(@PathVariable Long buildingID, @PathVariable Long apartmentID){
        Optional<Apartment> possibleApartment = getBuildingById(buildingID).getApartments().stream().filter(eachApartment -> eachApartment.getApartmentId().equals(apartmentID)).findFirst();
        if(possibleApartment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Unknown apartmentID: %d",apartmentID));
        }
        return possibleApartment.get();

    }

    private Boolean isContractCurrent(Contract contract){
        return contract.getContractEndDate()==null;
    }

    @GetMapping("/{buildingID}/apartments/{apartmentID}/contracts")
    public List<Contract> getContractsForApartment(@PathVariable Long buildingID, @PathVariable Long apartmentID, @RequestParam Optional<Boolean> current){
        Apartment apartment = getApartmentByBuilding(buildingID,apartmentID);
        if(current.isEmpty()){
            return apartment.getContracts();
        }
        Boolean isCurrent = current.get();
        return apartment.getContracts().stream().filter(contract -> isContractCurrent(contract).equals(isCurrent)).toList();


    }
    @PostMapping("/{buildingID}/apartments/{apartmentID}/contracts")
    public void addContract(@PathVariable Long buildingID, @PathVariable Long apartmentID, @RequestBody ContractDTO contractDTO){
        List<Contract> contracts = getContractsForApartment(buildingID,apartmentID,Optional.of(true));
        if(contracts.size()>0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Apartment currently rented");
        }
        Optional<Person> tenant = peopleRepo.findByPersonId(contractDTO.personID());
        if(tenant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unknown personID: "+ contractDTO.personID());
        }
        Person realTenant = tenant.get();
        if(realTenant.getIdNumber() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Person does not have necessary details to create a contract (No ID number)");
        }
        Apartment apartment =getApartmentByBuilding(buildingID,apartmentID);
        Contract contract = new Contract();
        contract.setApartment(apartment);
        contract.setContractStartDate(LocalDateTime.now());
        contract.setPerson(realTenant);
        contractRepo.save(contract);
    }

    @GetMapping("/{buildingID}/apartments/{apartmentID}/contracts/{contractID}")
    public Contract getContractByID(@PathVariable Long buildingID, @PathVariable Long apartmentID, @PathVariable Long contractID){
        List<Contract> contracts = getContractsForApartment(apartmentID,buildingID,Optional.empty());
        Optional<Contract> requestedContract = contracts.stream().filter(contract -> contract.getContractId() == contractID).findFirst();
        if(requestedContract.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown contractID: "+contractID);
        }
        return  requestedContract.get();

    }
    @DeleteMapping("/{buildingID}/apartments/{apartmentID}/contracts/{contractID}")
    public void endContract(@PathVariable Long buildingID, @PathVariable Long apartmentID, @PathVariable Long contractID){
        Contract contract = getContractByID(buildingID,apartmentID,contractID);
        if(!isContractCurrent(contract)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contract already finished");
        }
        contractRepo.uspEndContract(contract.getContractId());
    }


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="400",description = "No Change in current details"),
                    @ApiResponse(responseCode="403",description = "Contract already finished"),
                    @ApiResponse(responseCode = "200", description="OK")
            }
    )
    @PatchMapping("/{buildingID}/apartments/{apartmentID}/contracts/{contractID}")
    public void editContract(@PathVariable Long buildingID, @PathVariable Long apartmentID, @PathVariable Long contractID, @RequestBody ContractEditDTO contractEditDTO){
        Contract contract = getContractByID(buildingID,apartmentID,contractID);
        if(!isContractCurrent(contract)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to edit contract, Contract already finished");
        }
        Long currentBuildingID = contract.getApartment().getBuilding().getBuildingId();
        Long newBuildingID = contractEditDTO.newBuildingID().orElse(currentBuildingID);
        Long currentApartmentNumber = contract.getApartment().getNumber();
        Long newApartmentNumber = contractEditDTO.newApartmentNumber();
        if(currentBuildingID.equals(newBuildingID) && currentApartmentNumber.equals(newApartmentNumber)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Difference in newApartmentNumber or newBuildingID");
        }
        contractRepo.uspContractChangeApartment(contractID,newApartmentNumber,newBuildingID);

    }
}
