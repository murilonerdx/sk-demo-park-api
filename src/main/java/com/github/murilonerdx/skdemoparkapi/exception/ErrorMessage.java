package com.github.murilonerdx.skdemoparkapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
    private String path;
    private String method;
    private int status;
    private  String statusText;
    private String message;
    private Map<String, String> errors = new HashMap<>();


    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message){
        this.path = request.getServletPath();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result){
        this.path = request.getServletPath();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;

        addErrors(result);
    }

    public void addErrors(BindingResult result){
        result.getFieldErrors().forEach(error -> this.errors.put(error.getField(), error.getDefaultMessage()));
    }
}
