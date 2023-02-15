package org.example.repositories;

import org.example.entities.Apartment;
import org.example.entities.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment,Long> {

    Optional<Apartment> findByApartmentID(Long apartmentID);

    Iterable<Apartment> findByBuildingID(Building buildingID);
}
