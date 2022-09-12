package ru.zarwlad.hlarchitectcourse.service;

import java.util.List;

public interface CRUDService<T> {
    Boolean create(T t);
    Boolean update(T t);
    T get(Long id);
    List<T> getPage(Integer limit, Integer offset);
    Boolean delete (T t);
}
