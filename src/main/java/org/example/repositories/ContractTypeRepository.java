package org.example.repositories;

import org.example.entities.ContractType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractTypeRepository extends CrudRepository<ContractType,Long> {

    Optional<ContractType> findByName(String name);
}
