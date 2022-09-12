package ru.zarwlad.hlarchitectcourse.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.entity.Gender;
import ru.zarwlad.hlarchitectcourse.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getLong("id"));
        person.setEmail(rs.getString("email"));
        person.setLogin(rs.getString("login"));
        person.setPassword(rs.getString("password"));
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setAge(rs.getInt("age"));
        person.setGender(Gender.valueOf(rs.getString("gender")));
        person.setCity(new City(
                rs.getLong("city_id"),
                rs.getString("city"),
                rs.getString("region")
        ));

        return person;
    }
}
