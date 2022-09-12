package ru.zarwlad.hlarchitectcourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class City {
    private Long id;
    private String name;
    private String region;
}
