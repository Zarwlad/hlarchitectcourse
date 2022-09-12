package ru.zarwlad.hlarchitectcourse.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.PersonInterest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonInterestMapper implements RowMapper<PersonInterest> {
    @Override
    public PersonInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PersonInterest(
                rs.getLong("id"),
                rs.getLong("person_id"),
                new Interest(rs.getLong("interest_id"), rs.getString("tag"))
        );
    }
}
