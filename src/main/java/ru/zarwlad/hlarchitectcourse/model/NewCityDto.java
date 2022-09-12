package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@JsonAutoDetect
public class NewCityDto {
    @NotNull(message = "City name cannot be null")
    @Size(max = 255, message = "City name must be lower than 255 characters")
    private String name;

    @NotNull(message = "Region name cannot be null")
    @Size(max = 255, message = "Region name must be lower than 255 characters")
    private String region;
}
