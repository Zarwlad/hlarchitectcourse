package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.zarwlad.hlarchitectcourse.entity.Gender;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@JsonAutoDetect
public class NewPersonDto {
    private static final String PASSWORD_VALIDATION_MSG = "Password must contain:, * at least one digit [0-9], at least one lowercase Latin character [a-z], at least one uppercase Latin character [A-Z], at least one special character like ! @ # & ( ), a length of at least 8 characters.";


    @Email(message = "Email validation failed")
    @NotNull(message = "Email cannot be null")
    @Size(min = 1, max = 255, message = "Email must be lower than 255 characters")
    private String email;

    @NotNull(message = "Login cannot be null")
    @Size(min = 1, max = 255, message = "Login must be lower than 255 characters")
    private String login;

    @NotNull(message = "Password cannot be null")
    @Size(min = 1, max = 255, message = "Password must be lower than 255 characters")
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,255}$", message = PASSWORD_VALIDATION_MSG)
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be lower than 500 characters")
    private String name;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 1, max = 255, message = "Surname must be lower than 500 characters")
    private String surname;

    @NotNull(message = "Age cannot be null")
    @Positive(message = "Age cannot be negative")
    @Max(value = 99, message = "Age cannot be more than 99")
    private Integer age;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    private List<NewInterestDto> newInterestDtoList;

    @NotNull(message = "City cannot be null")
    private NewCityDto newCityDto;
}
