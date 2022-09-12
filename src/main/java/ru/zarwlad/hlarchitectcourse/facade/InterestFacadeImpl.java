package ru.zarwlad.hlarchitectcourse.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.dtomapper.InterestDtoMapper;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;
import ru.zarwlad.hlarchitectcourse.service.InterestService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InterestFacadeImpl implements InterestFacade{
    @Autowired
    private InterestService interestService;

    @Autowired
    private InterestDtoMapper interestDtoMapper;

    @Override
    public Boolean create(NewInterestDto newInterestDto) {
        return interestService.create(interestDtoMapper.fromNewDto(newInterestDto));
    }

    @Override
    public Boolean update(InterestDto interestDto) {
        return interestService.update(interestDtoMapper.fromDto(interestDto));
    }

    @Override
    public InterestDto get(Long id) {
        return interestDtoMapper.toDto(interestService.get(id));
    }

    @Override
    public List<InterestDto> getPage(Integer limit, Integer offset) {
        return interestService.getPage(limit, offset).stream()
                .map(interest -> interestDtoMapper.toDto(interest))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(InterestDto interestDto) {
        return interestService.delete(interestDtoMapper.fromDto(interestDto));
    }
}
