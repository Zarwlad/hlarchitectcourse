package ru.zarwlad.hlarchitectcourse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


// Вспомогательный класс для массового извлечения интересов пользователей в getPage по person'ам.

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PersonInterest {
    private Long id;
    private Long personId;
    private Interest interest;
}
