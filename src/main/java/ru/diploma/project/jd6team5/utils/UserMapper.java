package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.User;

import java.nio.charset.StandardCharsets;

@Component
public class UserMapper {

    public UserDto entityToDto (User user) {
        byte[] avatar = user.getAvatarPath() == null ? null : user.getAvatarPath();
        return new UserDto(
                user.getEmail(),
                user.getFirstName(),
                user.getUserID(),
                user.getLastName(),
                user.getPhone(),
                user.getRegDate(),
                "Город",
                avatar
        );
    }

    public User dtoToUser(UserDto dto) {
        byte[] avatar = dto.getImage() == null ? null : dto.getImage();
        return new User(dto.getId(),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(), //TODO: что-то ещё
                null, //TODO: как-то заполнять пароль
                dto.getPhone(),
                UserRole.USER,
                avatar,
                dto.getRegDate()
                );
    }
}