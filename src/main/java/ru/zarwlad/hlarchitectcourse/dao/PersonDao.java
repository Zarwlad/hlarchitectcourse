package ru.zarwlad.hlarchitectcourse.dao;

import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.entity.PersonInterest;

import java.util.List;
import java.util.Optional;

public interface PersonDao extends Dao<Person> {
    Optional<List<Interest>> findAllInterestsOfPersonByPersonId(Long personId);
    Boolean addNewInterestToPerson(Long personId, Interest interest);
    Boolean removeInterestFromPerson(Long personId, Interest interest);
    Optional<Person> findByLogin(String personLogin);
    Optional<List<PersonInterest>> findAllInterestsOfPersonsByPersonIds(List<Long> personIds);
    Optional<List<Person>> findByNameLikeAndSurnameLikePaged(String likeName, String likeSurname, Integer limit, Integer offset);
}
