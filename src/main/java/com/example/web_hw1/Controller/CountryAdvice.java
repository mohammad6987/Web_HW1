package com.example.web_hw1.Controller;



import com.example.web_hw1.Exception.CountryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class CountryAdvice {
    @ExceptionHandler(value = CountryNotFoundException.class)
    public ResponseEntity<Object> exception(CountryNotFoundException exception) {
        return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
    }
}
