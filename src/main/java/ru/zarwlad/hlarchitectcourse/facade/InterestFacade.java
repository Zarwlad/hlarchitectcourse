package ru.zarwlad.hlarchitectcourse.facade;

import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;
import ru.zarwlad.hlarchitectcourse.model.NewInterestDto;

public interface InterestFacade extends Facade<InterestDto, NewInterestDto, Interest> {
}
