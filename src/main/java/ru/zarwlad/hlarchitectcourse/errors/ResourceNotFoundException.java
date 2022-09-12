package ru.zarwlad.hlarchitectcourse.errors;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}
