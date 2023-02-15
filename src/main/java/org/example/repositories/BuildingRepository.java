package org.example.repositories;


import org.example.entities.BuildingWithApartments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingRepository extends CrudRepository<BuildingWithApartments,Long> {

    Optional<BuildingWithApartments> findByBuildingId(Long buildingId);
}
