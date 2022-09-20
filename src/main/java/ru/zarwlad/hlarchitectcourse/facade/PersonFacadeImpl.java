package ru.zarwlad.hlarchitectcourse.facade;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.dtomapper.InterestDtoMapper;
import ru.zarwlad.hlarchitectcourse.dtomapper.PersonDtoMapper;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewPersonDto;
import ru.zarwlad.hlarchitectcourse.model.PersonDto;
import ru.zarwlad.hlarchitectcourse.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class PersonFacadeImpl implements PersonFacade{
    @Autowired
    PersonDtoMapper personDtoMapper;
    @Autowired
    PersonService personService;
    @Autowired
    InterestDtoMapper interestDtoMapper;

    @Override
    public Boolean create(NewPersonDto newPersonDto) {
        return personService.create(personDtoMapper.fromNewDto(newPersonDto));
    }

    @Override
    public Boolean update(PersonDto personDto) {
        return personService.update(personDtoMapper.fromDto(personDto));
    }

    @Override
    public PersonDto get(Long id) {
        return personDtoMapper.toDto(personService.get(id));
    }

    @Override
    public List<PersonDto> getPage(Integer limit, Integer offset) {
        List<Person> people = personService.getPage(limit, offset);
        return people.stream().map(person -> personDtoMapper.toDto(person)).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(PersonDto personDto) {
        return personService.delete(personDtoMapper.fromDto(personDto));
    }

    @Override
    public List<InterestDto> findAllInterestsOfPersonByPersonId(Long personId) {
        return personService.findAllInterestsOfPersonByPersonId(personId).stream()
                .map(interest -> interestDtoMapper.toDto(interest))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean addNewInterestToPerson(Long personId, NewInterestDto newInterestDto) {
        return personService.addNewInterestToPerson(personId, interestDtoMapper.fromNewDto(newInterestDto));
    }

    @Override
    public Boolean removeInterestFromPerson(Long personId, InterestDto interestDto) {
        return personService.removeInterestFromPerson(personId, interestDtoMapper.fromDto(interestDto));
    }

    @Override
    public PersonDto findByLogin(String personLogin) {
        return personDtoMapper.toDto(personService.findByLogin(personLogin));
    }

    @Override
    public List<PersonDto> findByNameLikeAndSurnameLikePaged(String likeName, String likeSurname, Integer limit, Integer offset) {
        return personService
                .findByNameLikeAndSurnameLikePaged(likeName, likeSurname, limit, offset)
                .stream()
                .map(person -> personDtoMapper.toDto(person))
                .collect(Collectors.toList());
    }
}
