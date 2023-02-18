package ru.diploma.project.jd6team5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Нет запроса на регистрацию")
public class RegisterReqNotFoundException extends RuntimeException{
    public RegisterReqNotFoundException(String message) {
        super(message);
    }
}
