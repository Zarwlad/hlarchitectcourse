package ru.zarwlad.hlarchitectcourse.controller;

import ru.zarwlad.hlarchitectcourse.model.CityDto;
import ru.zarwlad.hlarchitectcourse.model.NewCityDto;

public interface CityController extends Controller<CityDto, NewCityDto> {
    CityDto findByCityAndRegion(String city, String region);
}
