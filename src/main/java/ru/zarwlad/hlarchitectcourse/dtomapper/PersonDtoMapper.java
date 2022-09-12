package ru.zarwlad.hlarchitectcourse.dtomapper;

import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.model.NewPersonDto;
import ru.zarwlad.hlarchitectcourse.model.PersonDto;

public interface PersonDtoMapper extends DtoMapper<PersonDto, Person, NewPersonDto> {
}
