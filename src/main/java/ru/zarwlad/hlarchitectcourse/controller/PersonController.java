package ru.zarwlad.hlarchitectcourse.controller;

import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewPersonDto;
import ru.zarwlad.hlarchitectcourse.model.PersonDto;

import java.util.List;

public interface PersonController extends Controller<PersonDto, NewPersonDto>{
    List<InterestDto> findAllInterestsOfPersonByPersonId(Long personId);
    void addNewInterestToPerson(Long personId, NewInterestDto newInterestDto);
    void removeInterestFromPerson(Long personId, InterestDto interestDto);
    PersonDto findByLogin(String personLogin);
}
