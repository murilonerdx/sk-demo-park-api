package com.github.murilonerdx.skdemoparkapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerHandler  {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> errorNotFoundException(NotFoundException e, HttpServletRequest request, BindingResult result){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage(), result)
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, BindingResult result){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result)
                );
    }

}
