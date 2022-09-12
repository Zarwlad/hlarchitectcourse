package ru.zarwlad.hlarchitectcourse.facade;

import java.util.List;

public interface Facade<D, N, E> {
    Boolean create(N n);
    Boolean update(D d);
    D get(Long id);
    List<D> getPage(Integer limit, Integer offset);

    Boolean delete (D d);
}
