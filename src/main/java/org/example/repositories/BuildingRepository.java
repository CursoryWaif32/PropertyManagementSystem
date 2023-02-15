package org.example.repositories;


import org.example.entities.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingRepository extends CrudRepository<Building,Long> {

    Optional<Building> findByBuildingId(Long buildingId);
}
