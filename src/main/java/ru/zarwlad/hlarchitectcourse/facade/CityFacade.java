package ru.zarwlad.hlarchitectcourse.facade;

import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.model.CityDto;
import ru.zarwlad.hlarchitectcourse.model.NewCityDto;

public interface CityFacade extends Facade<CityDto, NewCityDto, City> {
    CityDto findByCityAndRegion(String city, String region);
}
