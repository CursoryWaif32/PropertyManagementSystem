package org.example.controllers;

import org.example.entities.Contract;
import org.example.repositories.ContractRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/contracts")
public class ContractsController {

    final ContractRepository contractRepo;


    public ContractsController(ContractRepository contractRepo) {
        this.contractRepo = contractRepo;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return contract.get();
    }
}
