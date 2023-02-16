package org.example.dto;

import java.util.List;
import java.util.Optional;

public record BuildingEditDTO(Optional<String> address, Optional<List<ApartmentDTO>> addedApartments){

}
