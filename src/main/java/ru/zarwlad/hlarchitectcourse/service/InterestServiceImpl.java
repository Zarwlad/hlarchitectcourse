package ru.zarwlad.hlarchitectcourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zarwlad.hlarchitectcourse.dao.InterestDao;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.dtomapper.DtoMapper;
import ru.zarwlad.hlarchitectcourse.errors.ResourceNotFoundException;
import ru.zarwlad.hlarchitectcourse.errors.SavingDataException;
import ru.zarwlad.hlarchitectcourse.model.InterestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{
    @Autowired
    InterestDao interestDao;

    @Override
    public Boolean create(Interest interest) {
        Interest savedInterest = interestDao.findByTag(interest.getTag()).orElseGet(() -> new Interest(-1L, "undefined"));
        if (savedInterest.getId() == -1L)
            return interestDao.create(interest);
        else
            throw new SavingDataException(String.format("Interest with tag %s already exists", interest.getTag()));
    }

    @Override
    public Boolean update(Interest interest) {
        interestDao.getById(interest.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Interest with id %d and tag %s has not been found", interest.getId(), interest.getTag()))
                );
        return interestDao.update(interest);
    }

    @Override
    public Interest get(Long id) {
        return interestDao
                .getById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("Interest with id %d has not been found", id)
                        )
                );
    }

    @Override
    public List<Interest> getPage(Integer limit, Integer offset) {
        return interestDao
                .getAllPaged(limit, offset)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("The interest page is empty for limit %d, offset %d", limit, offset)
                        )
                );
    }

    @Override
    public Boolean delete(Interest interest) {
        interestDao.getById(interest.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Interest with id %d and tag %s has not been found", interest.getId(), interest.getTag()))
                );
        return interestDao.delete(interest);
    }
}
