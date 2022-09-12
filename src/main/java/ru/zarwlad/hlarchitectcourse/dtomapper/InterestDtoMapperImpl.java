package ru.zarwlad.hlarchitectcourse.dtomapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;

@Component
public class InterestDtoMapperImpl implements InterestDtoMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Interest fromDto(InterestDto dto) {
        return modelMapper.map(dto, Interest.class);
    }

    @Override
    public InterestDto toDto(Interest entity) {
        return modelMapper.map(entity, InterestDto.class);
    }

    @Override
    public Interest fromNewDto(NewInterestDto newDto) {
        return modelMapper.map(newDto, Interest.class);
    }
}
