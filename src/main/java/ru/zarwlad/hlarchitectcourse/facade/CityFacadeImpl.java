package ru.zarwlad.hlarchitectcourse.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.dtomapper.CityDtoMapper;
import ru.zarwlad.hlarchitectcourse.model.CityDto;
import ru.zarwlad.hlarchitectcourse.model.NewCityDto;
import ru.zarwlad.hlarchitectcourse.rowmapper.CityMapper;
import ru.zarwlad.hlarchitectcourse.service.CityService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CityFacadeImpl implements CityFacade{
    @Autowired
    CityService cityService;

    @Autowired
    CityDtoMapper cityDtoMapper;

    @Override
    public Boolean create(NewCityDto newCityDto) {
        return cityService.create(cityDtoMapper.fromNewDto(newCityDto));
    }

    @Override
    public Boolean update(CityDto cityDto) {
        return cityService.update(cityDtoMapper.fromDto(cityDto));
    }

    @Override
    public CityDto get(Long id) {
        return cityDtoMapper.toDto(cityService.get(id));
    }

    @Override
    public List<CityDto> getPage(Integer limit, Integer offset) {
        return cityService.getPage(limit, offset).stream()
                .map(city -> cityDtoMapper.toDto(city)).collect(Collectors.toList());
    }

    @Override
    public Boolean delete(CityDto cityDto) {
        return cityService.delete(cityDtoMapper.fromDto(cityDto));
    }

    @Override
    public CityDto findByCityAndRegion(String city, String region) {
        return cityDtoMapper.toDto(cityService.findByCityAndRegion(city, region));
    }
}
