package org.example.dto;

import java.util.Optional;

public record ContractEditDTO(Optional<Long> buildingID, Long apartmentNumber) {
}
