package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@JsonAutoDetect
@NoArgsConstructor
public class FriendDto {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "Non-positive id is not allowed")
    private Long id;

    @NotNull(message = "Person must be defined")
    private PersonDto personDto;

    @NotNull(message = "Friend must be defined")
    private PersonDto friendDto;

    @NotNull(message = "Friendship acceptance must be defined")
    private Boolean isAccepted;
}
