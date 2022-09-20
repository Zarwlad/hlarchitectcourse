package ru.zarwlad.hlarchitectcourse.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.zarwlad.hlarchitectcourse.errors.ResourceNotFoundException;
import ru.zarwlad.hlarchitectcourse.errors.SavingDataException;
import ru.zarwlad.hlarchitectcourse.model.ErrorDto;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> catchSavingDataException(SavingDataException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> catchMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> catchServerException(RuntimeException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(
                new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong. Try later."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ErrorDto> catchMessageNotReadableException(HttpMessageNotReadableException e){
//        log.error("Message is not readable. Input: " + e.getHttpInputMessage());
//        return new ResponseEntity<>(
//                new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        "Something went wrong. Try later."),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> catchJsonParseException(JsonParseException e){
        log.error("Message is not readable. Input: " + e.getRequestPayloadAsString());
        return new ResponseEntity<>(
                new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong. Try later."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
