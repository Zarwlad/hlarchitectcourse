package ru.zarwlad.hlarchitectcourse.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService extends CRUDService<Person>, UserDetailsService {
    List<Interest> findAllInterestsOfPersonByPersonId(Long personId);
    Boolean addNewInterestToPerson(Long personId, Interest interest);
    Boolean removeInterestFromPerson(Long personId, Interest interest);
    Person findByLogin(String personLogin);
}
