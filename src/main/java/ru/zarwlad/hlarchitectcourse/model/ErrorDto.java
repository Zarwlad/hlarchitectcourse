package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonAutoDetect
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorDto {
    private Integer code;
    private String message;
}
