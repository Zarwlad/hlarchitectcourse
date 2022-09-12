package ru.zarwlad.hlarchitectcourse.dao;

import ru.zarwlad.hlarchitectcourse.entity.City;

import java.util.Optional;

public interface CityDao extends Dao<City>{
    Optional<City> findByCityAndRegion(String city, String region);
}
