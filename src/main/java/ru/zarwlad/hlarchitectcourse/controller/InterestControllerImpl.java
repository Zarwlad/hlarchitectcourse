package ru.zarwlad.hlarchitectcourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zarwlad.hlarchitectcourse.facade.InterestFacade;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${BASE_URL}/interests")
@RequiredArgsConstructor
public class InterestControllerImpl implements InterestsController{
    @Autowired
    InterestFacade interestFacade;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InterestDto getById(@PathVariable Long id) {
        return interestFacade.get(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InterestDto> getAll(@RequestParam int limit, @RequestParam int offset) {
        return interestFacade.getPage(limit, offset);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody NewInterestDto newInterestDto) {
        interestFacade.create(newInterestDto);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody InterestDto interestDto) {
        interestFacade.update(interestDto);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Valid @RequestBody InterestDto interestDto) {
        interestFacade.delete(interestDto);
    }
}