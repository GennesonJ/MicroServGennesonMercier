package com.example.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseBody
    @ExceptionHandler(NewUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundException(NewUserNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors())
        {
            String name = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(name, errorMessage);
        }
        return errors;
    }

    @ResponseBody
    @ExceptionHandler(NewUserNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userNotValidException(NewUserNotValidException ex){
        return ex.getMessage();
    }

}
