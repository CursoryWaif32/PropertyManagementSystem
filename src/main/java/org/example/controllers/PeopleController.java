package org.example.controllers;

import org.example.entities.Person;
import org.example.repositories.PeopleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
