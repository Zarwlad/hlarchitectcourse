package ru.zarwlad.hlarchitectcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.rowmapper.CityMapper;
import ru.zarwlad.hlarchitectcourse.rowmapper.PersonMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CityDaoImpl implements CityDao{
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM cities WHERE id = ?";
    private static final String SQL_FIND_ALL_PAGED = "SELECT * FROM cities ORDER BY id LIMIT ? OFFSET ?";
    private static final String SQL_CREATE = "INSERT INTO cities (city, region) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE cities SET city = ?, region = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM cities WHERE id = ?";
    private static final String SQL_FIND_ALL_BY_CITY_AND_REGION = "SELECT * FROM cities WHERE city = ? AND region = ?";

    @Override
    public Optional<City> getById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new CityMapper(), id));
    }

    @Override
    public Optional<List<City>> getAllPaged(Integer limit, Integer offset) {
        return Optional.of(jdbcTemplate.query(SQL_FIND_ALL_PAGED, new CityMapper(), limit, offset));
    }

    @Override
    public Boolean update(City city) {
        return jdbcTemplate.update(SQL_UPDATE, city.getName(), city.getRegion(), city.getId()) > 0;
    }

    @Override
    public Boolean create(City city) {
        return jdbcTemplate.update(SQL_CREATE, city.getName(), city.getRegion()) > 0;
    }

    @Override
    public Boolean delete(City city) {
        return jdbcTemplate.update(SQL_DELETE, city.getId()) > 0;
    }

    @Override
    public Optional<City> findByCityAndRegion(String city, String region) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_CITY_AND_REGION, new CityMapper(), city, region).stream().findFirst();
    }
}
