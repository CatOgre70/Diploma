package ru.diploma.project.jd6team5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Неверный пароль")
public class BadPasswordException extends RuntimeException{
    public BadPasswordException(String message) {
        super(message);
    }
}
