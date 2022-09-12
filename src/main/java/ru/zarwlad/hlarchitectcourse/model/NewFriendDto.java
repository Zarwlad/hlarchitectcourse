package ru.zarwlad.hlarchitectcourse.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@JsonAutoDetect
@AllArgsConstructor
@Data
public class NewFriendDto {
    @NotNull(message = "Person must be defined")
    private NewPersonDto newPersonDto;

    @NotNull(message = "Friend must be defined")
    private NewPersonDto newFriendDto;

    @NotNull(message = "Friendship acceptance must be defined")
    private Boolean isAccepted;
}
