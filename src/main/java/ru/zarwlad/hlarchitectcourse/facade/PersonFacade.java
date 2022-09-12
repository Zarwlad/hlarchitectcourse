package ru.zarwlad.hlarchitectcourse.facade;

import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewPersonDto;
import ru.zarwlad.hlarchitectcourse.model.PersonDto;

import java.util.List;

public interface PersonFacade extends Facade<PersonDto, NewPersonDto, Person> {
    List<InterestDto> findAllInterestsOfPersonByPersonId(Long personId);
    Boolean addNewInterestToPerson(Long personId, NewInterestDto newInterestDto);
    Boolean removeInterestFromPerson(Long personId, InterestDto interestDto);
    PersonDto findByLogin(String personLogin);
}
