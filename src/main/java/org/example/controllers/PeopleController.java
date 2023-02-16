package org.example.controllers;

import org.example.dto.PersonDTO;
import org.example.entities.Email;
import org.example.entities.Person;
import org.example.entities.PhoneNumber;
import org.example.repositories.PeopleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/{id}")
    public Person getPersonByID(@PathVariable Long id){
        Optional<Person> person = peopleRepo.findByPersonId(id);
        if(person.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return person.get();
    }

    @PatchMapping("/{id}")
    public void updatePersonDetails(@PathVariable Long id, @RequestBody PersonDTO personDTO){
        Person person = getPersonByID(id);
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
        person.setIdNumber(personDTO.idNumber().orElse(person.getIdNumber()));
        peopleRepo.save(person);
    }
}
