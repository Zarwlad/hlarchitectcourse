package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@JsonAutoDetect
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class InterestDto {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "Non-positive id is not allowed")
    private Long id;

    @NotNull(message = "Tag cannot be null")
    @Size(min = 1, max = 100, message = "Tag must be lower than 100 characters")
    private String tag;
}
