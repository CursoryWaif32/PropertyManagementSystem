package org.example.controllers;

import org.example.entities.ContractType;
import org.example.repositories.ContractTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
public class ContractsController {

    final ContractTypeRepository contractTypeRepo;

    public ContractsController(ContractTypeRepository contractTypeRepo) {
        this.contractTypeRepo = contractTypeRepo;
    }

    @GetMapping("/types")
    public Iterable<ContractType> getContractTypes(){
        return contractTypeRepo.findAll();
    }
}
