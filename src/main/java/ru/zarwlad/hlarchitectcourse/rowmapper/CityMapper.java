package ru.zarwlad.hlarchitectcourse.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zarwlad.hlarchitectcourse.entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        City city = new City();
        city.setId(rs.getLong("id"));
        city.setName(rs.getString("city"));
        city.setRegion(rs.getString("region"));
        return city;
    }
}
