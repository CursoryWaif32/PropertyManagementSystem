package org.example.repositories;

import org.example.entities.Contract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository  extends CrudRepository<Contract,Long> {

    Optional<Contract> findByContractId(Long id);
}
