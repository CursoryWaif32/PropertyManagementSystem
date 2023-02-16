package org.example.dto;

import java.util.List;
import java.util.Optional;

public record BuildingAddDTO (String address, Optional<List<ApartmentDTO>> apartments){
}
