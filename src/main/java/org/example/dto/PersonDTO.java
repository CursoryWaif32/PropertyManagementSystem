package org.example.dto;

import org.example.entities.PersonType;

import java.util.List;
import java.util.Optional;

public record PersonDTO (Optional<String> firstName, Optional<String> lastName, Optional<String> idNumber, Optional<List<PhoneNumberDTO>>phoneNumbers, Optional<List<EmailDTO>> emails, Optional<PersonType> personType) {
}
