package services;

import models.Person;
import org.springframework.stereotype.Service;
import repositories.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Iterable<Person> getAll(){
        return  personRepository.findAll();
    }
}
