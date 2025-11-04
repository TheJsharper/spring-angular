package controllers;

import models.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.PersonService;

@RestController
@RequestMapping("api")
public class RestEndPointController {

    private final PersonService personService;

    public RestEndPointController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public Iterable<Person> getAll() {

        return personService.getAll();
    }
}
