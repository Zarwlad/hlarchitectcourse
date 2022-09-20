package ru.zarwlad.hlarchitectcourse.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zarwlad.hlarchitectcourse.facade.PersonFacade;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewPersonDto;
import ru.zarwlad.hlarchitectcourse.model.PersonDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${BASE_URL}/person")
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class PersonControllerImpl implements PersonController{
    @Autowired
    PersonFacade personFacade;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PersonDto getById(@PathVariable Long id) {
        return personFacade.get(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<PersonDto> getAll(@RequestParam int limit, @RequestParam int offset) {
        return personFacade.getPage(limit, offset);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void create(@Valid @RequestBody NewPersonDto newPersonDto) {
        personFacade.create(newPersonDto);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void update(@Valid @RequestBody PersonDto personDto) {
        personFacade.update(personDto);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void delete(@Valid @RequestBody PersonDto personDto) {
        personFacade.delete(personDto);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/person-interests/{personId}")
    public List<InterestDto> findAllInterestsOfPersonByPersonId(@PathVariable Long personId) {
        return personFacade.findAllInterestsOfPersonByPersonId(personId);
    }

    @Override
    @PostMapping("/person-interests/{personId}/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewInterestToPerson(@PathVariable Long personId, @RequestBody NewInterestDto newInterestDto) {
        personFacade.addNewInterestToPerson(personId, newInterestDto);
    }

    @Override
    @PostMapping("/person-interests/{personId}/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeInterestFromPerson(@PathVariable Long personId, @RequestBody InterestDto interestDto) {
        personFacade.removeInterestFromPerson(personId, interestDto);
    }

    @Override
    @GetMapping("/by-login/{personLogin}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto findByLogin(@PathVariable String personLogin) {
        return personFacade.findByLogin(personLogin);
    }
    @Override
    @GetMapping("/by-name-and-surname")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> findByNameLikeAndSurnameLikePaged(@RequestParam String likeName,
                                                      @RequestParam String likeSurname,
                                                      @RequestParam Integer limit,
                                                      @RequestParam Integer offset){
        return personFacade.findByNameLikeAndSurnameLikePaged(likeName, likeSurname, limit, offset);
    }

}
