package ru.zarwlad.hlarchitectcourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zarwlad.hlarchitectcourse.dao.CityDao;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.errors.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    @Autowired
    CityDao cityDao;

    @Override
    public Boolean create(City city) {
        return cityDao.create(city);
    }

    @Override
    public Boolean update(City city) {
        return cityDao.update(city);
    }

    @Override
    public City get(Long id) {
        return cityDao.getById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("The city id %d has not been found", id)));
    }

    @Override
    public List<City> getPage(Integer limit, Integer offset) {
        return cityDao
                .getAllPaged(limit, offset)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("The city list is empty, limit %d, offset %d", limit, offset)
                ));
    }

    @Override
    public Boolean delete(City city) {
        return cityDao.delete(city);
    }

    @Override
    public City findByCityAndRegion(String city, String region) {
        return cityDao.findByCityAndRegion(city, region).orElseThrow(() -> new ResourceNotFoundException(
                String.format("City %s in region %s has not been found", city, region))
        );
    }
}
