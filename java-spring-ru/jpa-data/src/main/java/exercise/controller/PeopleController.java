package exercise.controller;

import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping()
    public ResponseEntity<List<Person>> getPostList() {
        var listOfPeople = personRepository.findAll().stream().toList();

        return ResponseEntity.ok()
                .body(listOfPeople);

    }

    @PostMapping()
    public ResponseEntity<Person> create(@RequestBody Person person) {
        personRepository.save(person);

        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void destroy(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
    // END
}