package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zarwlad.hlarchitectcourse.entity.Gender;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class PersonDto {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "Non-positive id is not allowed")
    private Long id;

    @Email(message = "Email validation failed")
    @NotNull(message = "Email cannot be null")
    @Size(min = 1, max = 255, message = "Email must be lower than 255 characters")
    private String email;

    @NotNull(message = "Login cannot be null")
    @Size(min = 1, max = 255, message = "Login must be lower than 255 characters")
    private String login;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 500, message = "Name must be lower than 500 characters")
    private String name;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 1, max = 500, message = "Surname must be lower than 500 characters")
    private String surname;

    @NotNull(message = "Age cannot be null")
    @Positive(message = "Age cannot be negative")
    @Max(value = 99, message = "Age cannot be more than 99")
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    private List<InterestDto> interestsDtoList;

    @NotNull(message = "City cannot be null")
    private CityDto cityDto;
}
