package ru.zarwlad.hlarchitectcourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zarwlad.hlarchitectcourse.facade.CityFacade;
import ru.zarwlad.hlarchitectcourse.model.CityDto;
import ru.zarwlad.hlarchitectcourse.model.NewCityDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${BASE_URL}/city")
@RequiredArgsConstructor
public class CityControllerImpl implements CityController{
    @Autowired
    CityFacade cityFacade;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDto getById(@PathVariable Long id) {
        return cityFacade.get(id);
    }

    @Override
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CityDto> getAll(@RequestParam int offset, @RequestParam int limit) {
        return cityFacade.getPage(limit, offset);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody NewCityDto newCityDto) {
        cityFacade.create(newCityDto);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody CityDto cityDto) {
        cityFacade.update(cityDto);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Valid @RequestBody CityDto cityDto) {
        cityFacade.delete(cityDto);
    }

    @Override
    @GetMapping("/city")
    public CityDto findByCityAndRegion(@RequestParam String city, @RequestParam String region) {
        return cityFacade.findByCityAndRegion(city, region);
    }
}
