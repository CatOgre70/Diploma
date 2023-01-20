package ru.diploma.project.jd6team5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.IM_USED, reason = "Новый пароль уже использовался")
public class NewPasswordAlreadyUsedException extends RuntimeException{
    public NewPasswordAlreadyUsedException(String message) {
        super(message);
    }
}
