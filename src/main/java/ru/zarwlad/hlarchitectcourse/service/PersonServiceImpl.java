package ru.zarwlad.hlarchitectcourse.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zarwlad.hlarchitectcourse.dao.CityDao;
import ru.zarwlad.hlarchitectcourse.dao.FriendDao;
import ru.zarwlad.hlarchitectcourse.dao.InterestDao;
import ru.zarwlad.hlarchitectcourse.dao.PersonDao;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.errors.ResourceNotFoundException;
import ru.zarwlad.hlarchitectcourse.errors.SavingDataException;
import ru.zarwlad.hlarchitectcourse.entity.PersonInterest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;

    @Autowired
    FriendDao friendDao;

    @Autowired
    InterestDao interestDao;

    @Autowired
    CityDao cityDao;

    @Override
    @Transactional
    public Boolean create(Person person) {
        personDao.findByLogin(person.getLogin())
                .ifPresent(s -> {
                    throw new SavingDataException(String.format("The person with login %s already exists", s.getLogin()));
                });

        List<Interest> allSavedInterests = new ArrayList<>();

        if (!person.getInterests().isEmpty() && person.getInterests() != null) {
            //Ищем уже существующие интересы
            List<Interest> foundInterests = interestDao
                    .findAllTagsByList(
                            person.getInterests().stream()
                                    .map(Interest::getTag)
                                    .collect(Collectors.toList())
                    )
                    .orElseGet(ArrayList::new);
            allSavedInterests.addAll(foundInterests);

            //Ищем новые интересы
            Set<String> uniqueTags = person.getInterests().stream()
                    .map(Interest::getTag)
                    .collect(Collectors.toSet());

            uniqueTags.removeAll(foundInterests.stream()
                    .map(Interest::getTag)
                    .collect(Collectors.toSet())
            );

            //сохраняем новые интересы
            List<Interest> savedInterests = uniqueTags.stream()
                    .map(tag -> {
                        Interest interestToSave = new Interest();
                        interestToSave.setTag(tag);
                        interestDao.create(interestToSave);
                        return interestDao.findByTag(interestToSave.getTag())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        String.format("Тег %s не был обнаружен. Попробуйте позднее", tag)));
                    })
                    .toList();
            allSavedInterests.addAll(savedInterests);
        }

        City savedCity = cityDao.findByCityAndRegion(person.getCity().getName(), person.getCity().getRegion()).orElse(new City(-1L, "", ""));
        if (savedCity.getId() == -1L) {
            cityDao.create(person.getCity());
            savedCity = cityDao.findByCityAndRegion(person.getCity().getName(), person.getCity().getRegion())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("City %s in region %s has not been found and can't be created",
                                    person.getCity().getName(),
                                    person.getCity().getRegion()
                            ))
                    );
        }
        person.setCity(savedCity);
        Boolean p = personDao.create(person);

        Person savedPerson = personDao.findByLogin(person.getLogin())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Person with login %s has not been created. Please try later", person.getLogin())));

        allSavedInterests.forEach(interest -> personDao.addNewInterestToPerson(savedPerson.getId(), interest));
        return p;
    }

    @Override
    public Boolean update(Person person) {
        personDao.getById(person.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Person with id %d and login %s doesn't exist", person.getId(), person.getLogin()))
                );

        return personDao.update(person);
    }

    @Override
    public Person get(Long id) {
        Person person = personDao.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The person id %d has not been found", id)));
        person.setInterests(findAllInterestsOfPersonByPersonId(id));
        return person;
    }

    @Override
    public List<Person> getPage(Integer limit, Integer offset) {
        List<Person> people = personDao.getAllPaged(limit, offset)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The person list is empty, limit %d, offset %d", limit, offset)));

        if (people != null && !people.isEmpty())
            return enrichPeopleByInterests(people);
        else
            return people;
    }

    private List<Person> enrichPeopleByInterests(List<Person> people) {
        List<Person> enrichedPersons = List.copyOf(people);
        List<PersonInterest> personInterests = personDao.findAllInterestsOfPersonsByPersonIds(
                        enrichedPersons.stream()
                                .map(Person::getId)
                                .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        enrichedPersons.forEach(person -> {
            List<Interest> interestList = new ArrayList<>();

            personInterests.forEach(pi -> {
                if (Objects.equals(pi.getPersonId(), person.getId()))
                    interestList.add(pi.getInterest());
            });

            person.setInterests(interestList);
        });
        return enrichedPersons;
    }

    @Override
    public Boolean delete(Person person) {
        personDao.getById(person.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Person with id %d and login %s doesn't exist", person.getId(), person.getLogin()))
                );

        return personDao.delete(person);
    }

    @Override
    public List<Interest> findAllInterestsOfPersonByPersonId(Long personId) {
        return personDao.findAllInterestsOfPersonByPersonId(personId).orElseGet(ArrayList::new);
    }

    @Override
    public Boolean addNewInterestToPerson(Long personId, Interest interest) {
        Interest savedInterest = interestDao.findByTag(interest.getTag()).orElse(new Interest(-1L, "Undefined"));

        Interest finalSavedInterest = savedInterest;
        Boolean personInterestExists = personDao
                .findAllInterestsOfPersonByPersonId(personId)
                .orElseGet(ArrayList::new)
                .stream()
                .anyMatch(checked -> checked.getTag().equals(finalSavedInterest.getTag()));
        if (personInterestExists) {
            throw new SavingDataException(String.format("The interest %s already has been added to the person with id %d", interest.getTag(), personId));
        }

        if (savedInterest.getId() == -1L) {
            interestDao.create(interest);
            savedInterest = interestDao.findByTag(interest.getTag())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Interest tag %s has not been saved. Try later", interest.getTag())));
        }

        return personDao.addNewInterestToPerson(personId, savedInterest);
    }

    @Override
    public Boolean removeInterestFromPerson(Long personId, Interest interest) {
        Interest savedInterest = interestDao.findByTag(interest.getTag())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Interest tag %s has not been found", interest.getTag())));

        personDao.findAllInterestsOfPersonByPersonId(personId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The person with id %d doesn't have interests", personId)))
                .stream()
                .filter(x -> x.getId() == interest.getId())
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The person with id %d doesn't have interest with tag %s", personId, interest.getTag())));

        return personDao.removeInterestFromPerson(personId, savedInterest);
    }

    @Override
    public Person findByLogin(String personLogin) {
        Person person = personDao.findByLogin(personLogin)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The person with login %s has not been found", personLogin)));
        person.setInterests(findAllInterestsOfPersonByPersonId(person.getId()));
        return person;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personDao.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with login %s has not been found", username)));
    }

    @Override
    public List<Person> findByNameLikeAndSurnameLikePaged(String likeName, String likeSurname, Integer limit, Integer offset) {
        List<Person> people = personDao.findByNameLikeAndSurnameLikePaged(likeName, likeSurname, limit, offset)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The person list is empty for the search with name %s surname %s limit %d offset %d", likeName, likeSurname, limit, offset)));

        if (people != null && !people.isEmpty()) return enrichPeopleByInterests(people);
        else return people;
    }
}
