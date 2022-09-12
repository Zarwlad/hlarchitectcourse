package ru.zarwlad.hlarchitectcourse.errors;

public class SavingDataException extends RuntimeException {
    public SavingDataException(String message){
        super(message);
    }
}
