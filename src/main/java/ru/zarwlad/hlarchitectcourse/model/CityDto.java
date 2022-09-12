package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@JsonAutoDetect
@NoArgsConstructor
public class CityDto {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "Non-positive id is not allowed")
    private Long id;

    @NotNull(message = "City name cannot be null")
    @Size(max = 255, message = "City name must be lower than 255 characters")
    private String name;

    @NotNull(message = "Region name cannot be null")
    @Size(max = 255, message = "Region name must be lower than 255 characters")
    private String region;
}
