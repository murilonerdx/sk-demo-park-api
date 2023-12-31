package com.github.murilonerdx.skdemoparkapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class RestControllerHandler  {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> errorNotFoundException(NotFoundException e, HttpServletRequest request, BindingResult result){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage(), result)
                );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> erroracessDeniedException(AccessDeniedException e, HttpServletRequest request, BindingResult result){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.FORBIDDEN, e.getMessage(), result)
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, BindingResult result){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result)
                );
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(UsernameExistException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getMessage())
                );
    }

    @ExceptionHandler(VagaExistException.class)
    public ResponseEntity<ErrorMessage> methodVagaExistException(VagaExistException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getMessage())
                );
    }

    @ExceptionHandler(CpfUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> methodCpfUniqueViolationException(CpfUniqueViolationException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage())
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> runtimeException(RuntimeException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getLocalizedMessage())
                );
    }
}
