package ru.zarwlad.hlarchitectcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.entity.PersonInterest;
import ru.zarwlad.hlarchitectcourse.rowmapper.InterestMapper;
import ru.zarwlad.hlarchitectcourse.rowmapper.PersonInterestMapper;
import ru.zarwlad.hlarchitectcourse.rowmapper.PersonMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM persons JOIN cities ON persons.city_id = cities.id WHERE persons.id = ? ORDER BY persons.id";
    private static final String SQL_FIND_ALL_PAGED = "SELECT * FROM persons JOIN cities ON persons.city_id = cities.id ORDER BY persons.id LIMIT ? OFFSET ? ";
    private static final String SQL_CREATE = "INSERT INTO persons (email, login, password, name, surname, age, gender, city_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE persons SET email = ?, login = ?, name = ?, " + "surname = ?, age = ?, gender = ?, city_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM persons WHERE id = ?";
    private static final String SQL_FIND_PERSON_INTERESTS = "SELECT person_id, interest_id as id, tag FROM persons_interests pi " + "JOIN interests i ON pi.interest_id = i.id WHERE person_id = ? ORDER BY pi.id";
    private static final String SQL_ADD_INTEREST_TO_PERSON = "INSERT INTO persons_interests (person_id, interest_id) VALUES (?, ?)";
    private static final String SQL_REMOVE_INTEREST_FROM_PERSON = "DELETE FROM persons_interests WHERE person_id = ? AND interest_id = ?";
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM persons JOIN cities ON persons.city_id = cities.id WHERE persons.login = ? ORDER BY persons.id";

    private static final String SQL_FIND_ALL_INTERESTS_OF_PERSONS = "SELECT * FROM persons_interests pi " + "JOIN interests i ON pi.interest_id = i.id WHERE person_id IN (:personIds) ORDER BY pi.id";

    private static final String SQL_FIND_BY_NAME_LIKE_AND_SURNAME_LIKE = """ 
            SELECT * 
            FROM persons 
            FORCE INDEX (persons_name_surname_i)
            JOIN cities ON persons.city_id = cities.id
            WHERE persons.name LIKE ? 
            AND persons.surname LIKE ? 
            ORDER BY persons.id 
            LIMIT ? OFFSET ?
            """;

    @Override
    public Optional<Person> getById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new PersonMapper(), id).stream().findFirst();
    }

    @Override
    public Optional<List<Person>> getAllPaged(Integer limit, Integer offset) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_ALL_PAGED, new PersonMapper(), limit, offset));
    }

    @Override
    public Boolean update(Person person) {
        return jdbcTemplate.update(SQL_UPDATE, person.getEmail(), person.getLogin(), person.getName(), person.getSurname(), person.getAge(), person.getGender().toString(), person.getCity().getId(), person.getId()) > 0;
    }

    @Override
    public Boolean create(Person person) {
        return jdbcTemplate.update(SQL_CREATE, person.getEmail(), person.getLogin(), person.getPassword(), person.getName(), person.getSurname(), person.getAge(), person.getGender().toString(), person.getCity().getId()) > 0;
    }

    @Override
    public Boolean delete(Person person) {
        return jdbcTemplate.update(SQL_DELETE, person.getId()) > 0;
    }

    @Override
    public Boolean addNewInterestToPerson(Long personId, Interest interest) {
        return jdbcTemplate.update(SQL_ADD_INTEREST_TO_PERSON, personId, interest.getId()) > 0;
    }

    @Override
    public Boolean removeInterestFromPerson(Long personId, Interest interest) {
        return jdbcTemplate.update(SQL_REMOVE_INTEREST_FROM_PERSON, personId, interest.getId()) > 0;
    }

    @Override
    public Optional<List<Interest>> findAllInterestsOfPersonByPersonId(Long personId) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_PERSON_INTERESTS, new InterestMapper(), personId));
    }

    @Override
    public Optional<Person> findByLogin(String personLogin) {
        return jdbcTemplate.query(SQL_FIND_BY_LOGIN, new PersonMapper(), personLogin).stream().findFirst();
    }

    @Override
    public Optional<List<PersonInterest>> findAllInterestsOfPersonsByPersonIds(List<Long> personIds) {
        SqlParameterSource parameter = new MapSqlParameterSource("personIds", personIds);
        return Optional.of(namedParameterJdbcTemplate.query(SQL_FIND_ALL_INTERESTS_OF_PERSONS, parameter, new PersonInterestMapper()));
    }

    @Override
    public Optional<List<Person>> findByNameLikeAndSurnameLikePaged(String likeName, String likeSurname, Integer limit, Integer offset) {
        likeName = likeName + "%";
        likeSurname = likeSurname + "%";

        return Optional.of(jdbcTemplate.query(SQL_FIND_BY_NAME_LIKE_AND_SURNAME_LIKE, new PersonMapper(), likeName, likeSurname, limit, offset));
    }
}
