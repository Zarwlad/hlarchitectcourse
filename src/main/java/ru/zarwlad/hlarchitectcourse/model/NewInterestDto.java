package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonAutoDetect
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewInterestDto {
    @NotNull(message = "Tag cannot be null")
    @Size(max = 100, message = "Tag must be lower than 100 characters")
    private String tag;
}
