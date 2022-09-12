package ru.zarwlad.hlarchitectcourse.dao;

import ru.zarwlad.hlarchitectcourse.entity.Interest;

import java.util.List;
import java.util.Optional;

public interface InterestDao extends Dao<Interest> {
    Optional<Interest> findByTag(String tag);
    Optional<List<Interest>> findAllTagsByList(List<String> tags);
}
