package org.example.repositories;

import org.example.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository  extends CrudRepository<Person,Long> {
    Optional<Person> findByPersonId(Long id);
}
