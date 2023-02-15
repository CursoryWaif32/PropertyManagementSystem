package org.example.repositories;

import org.example.entities.ApartmentWithBuilding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentWithBuilding,Long> {

    Optional<ApartmentWithBuilding> findByApartmentID(Long apartmentID);

}
