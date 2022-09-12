package ru.zarwlad.hlarchitectcourse.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface Controller<E, N> {
    @GetMapping("/{id}")
    E getById (@PathVariable Long id);

    @GetMapping("/all")
    List<E> getAll(@RequestParam int limit, @RequestParam int offset);

    @PostMapping
    void create(@RequestBody N n);

    @PutMapping
    void update (@RequestBody E e);

    @DeleteMapping
    void delete (@RequestBody E e);

}
