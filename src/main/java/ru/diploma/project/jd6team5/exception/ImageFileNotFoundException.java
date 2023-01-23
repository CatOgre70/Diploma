package ru.diploma.project.jd6team5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Не найден файл аватарки для Пользователя")
public class ImageFileNotFoundException extends RuntimeException{
    public ImageFileNotFoundException(String message) {
        super(message);
    }
}
