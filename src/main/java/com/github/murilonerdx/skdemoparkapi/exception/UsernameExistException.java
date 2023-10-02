package com.github.murilonerdx.skdemoparkapi.exception;

public class UsernameExistException extends RuntimeException {
    public UsernameExistException(String message) {
        super(message);
    }
}
