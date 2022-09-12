package ru.zarwlad.hlarchitectcourse.service;

import ru.zarwlad.hlarchitectcourse.entity.City;

import java.util.Optional;

public interface CityService extends CRUDService<City> {
    City findByCityAndRegion(String city, String region);
}
