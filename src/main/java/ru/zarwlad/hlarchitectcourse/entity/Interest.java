package ru.zarwlad.hlarchitectcourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Interest {
    private Long id;
    private String tag;
}
