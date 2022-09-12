package ru.zarwlad.hlarchitectcourse.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zarwlad.hlarchitectcourse.entity.Interest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterestMapper implements RowMapper<Interest> {
    @Override
    public Interest mapRow(ResultSet rs, int rowNum) throws SQLException {
        Interest interest = new Interest();
        interest.setId(rs.getLong("id"));
        interest.setTag(rs.getString("tag"));
        return interest;
    }
}
