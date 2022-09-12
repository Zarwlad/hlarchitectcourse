package ru.zarwlad.hlarchitectcourse.dtomapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.model.CityDto;
import ru.zarwlad.hlarchitectcourse.model.NewCityDto;

@Component
public class CityDtoMapperImpl implements CityDtoMapper{
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public City fromDto(CityDto dto) {
        return modelMapper.map(dto, City.class);
    }

    @Override
    public CityDto toDto(City entity) {
        return modelMapper.map(entity, CityDto.class);
    }

    @Override
    public City fromNewDto(NewCityDto newDto) {
        return modelMapper.map(newDto, City.class);
    }
}
