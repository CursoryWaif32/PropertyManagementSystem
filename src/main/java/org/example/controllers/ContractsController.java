package org.example.controllers;

import org.example.entities.Contract;
import org.example.repositories.ContractRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Iterable<Contract> getAllContracts(){
        return contractRepo.findAll();
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
