package de.solovyov.demo.api;

import de.solovyov.demo.model.Person;
import de.solovyov.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@Valid @NotNull @RequestBody Person person) {
        this.personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return this.personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        return this.personService.getPersonById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") UUID id) {
        this.personService.deletePerson(id);
    }

    @PutMapping(path ="{id}")
    public void updatePerson(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person person) {
        this.personService.updatePerson(id, person);
    }
}
