package org.example.repositories;

import org.example.entities.Contract;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ContractRepository  extends CrudRepository<Contract,Long> {

    Optional<Contract> findByContractId(Long id);

    @Procedure
    void uspCreateContract(Long personID, Long apartmentNumber,Long buildingID, Long ContractTypeID, Date startDate);

    Iterable<Contract> findContractByContractTypeNameLikeIgnoreCase(String name);
}
