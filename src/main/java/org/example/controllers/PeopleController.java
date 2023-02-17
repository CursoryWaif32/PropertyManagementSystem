package org.example.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.dto.PersonDTO;
import org.example.entities.Email;
import org.example.entities.Person;
import org.example.entities.PhoneNumber;
import org.example.repositories.PeopleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PeopleController {
    final PeopleRepository peopleRepo;

    public PeopleController(PeopleRepository peopleRepo) {
        this.peopleRepo = peopleRepo;
    }

    @GetMapping
    public Iterable<Person> getAllPeople(){
        return peopleRepo.findAll();
    }


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="404",description = "Unknown Person"),
                    @ApiResponse(responseCode = "200", description="OK"),
            }
    )
    @GetMapping("/{id}")
    public Person getPersonByID(@PathVariable Long id){
        Optional<Person> person = peopleRepo.findByPersonId(id);
        if(person.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Person found with ID: "+id);
        }
        return person.get();
    }

    @PatchMapping("/{id}")
    public void updatePersonDetails(@PathVariable Long id, @RequestBody PersonDTO personDTO){
        Person person = getPersonByID(id);
        updatePersonDetails(personDTO, person);
        peopleRepo.save(person);
    }

    @PostMapping
    void addPerson(@RequestBody PersonDTO personDTO){
        Person person = new Person();
        person.setEmails(new ArrayList<>());
        person.setPhoneNumbers(new ArrayList<>());
        updatePersonDetails(personDTO, person);
        peopleRepo.save(person);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="400",description = "Bad ID Number"),
                    @ApiResponse(responseCode = "200", description="OK"),
            }
    )
    private void updatePersonDetails(@RequestBody PersonDTO personDTO, Person person) {
        person.setFirstName(personDTO.firstName().orElse(person.getFirstName()));
        person.setLastName(personDTO.lastName().orElse(person.getLastName()));
        if(personDTO.emails().isPresent()){
            personDTO.emails().get().forEach(emailDTO -> {
                Email email = new Email();
                email.setEmail(emailDTO.email());
                person.getEmails().add(email);
            });
        }
        if(personDTO.phoneNumbers().isPresent()){
            personDTO.phoneNumbers().get().forEach(phoneNumberDTO -> {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(phoneNumberDTO.phoneNumber());
                person.getPhoneNumbers().add(phoneNumber);
            });
        }
        if(personDTO.idNumber().isPresent()){
            if(personDTO.idNumber().get().length()!=13)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ID not 13 characters");
            for(Character c: personDTO.idNumber().get().toCharArray()){
                if (!Character.isDigit(c)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ID contains non numeric values");
                }
            }
        }
        person.setIdNumber(personDTO.idNumber().orElse(person.getIdNumber()));
    }
}
