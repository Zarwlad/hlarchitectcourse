package ru.zarwlad.hlarchitectcourse.dao;

import ru.zarwlad.hlarchitectcourse.entity.Person;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(Long id);
    Optional<List<T>> getAllPaged(Integer limit, Integer offset);
    Boolean update(T t);
    Boolean create(T t);
    Boolean delete(T t);
}
