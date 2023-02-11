package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.User;

import java.nio.charset.StandardCharsets;

@Component
public class UserMapper {

    public UserDto entityToDto (User user) {
        String avatar = user.getAvatar() == null ? null : "/users/me/getavatar";
        return new UserDto(
                user.getEmail(),
                user.getFirstName(),
                user.getUserID(),
                user.getLastName(),
                user.getPhone(),
                user.getRegDate(),
                user.getCity(),
                avatar
        );
    }

    public User dtoToUser(UserDto dto) {
        String avatar = dto.getImage() == null ? null : dto.getImage();
        return new User(dto.getId(),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(), //TODO: что-то ещё
                null, //TODO: как-то заполнять пароль
                dto.getPhone(),
                UserRole.USER,
                avatar,
                dto.getRegDate(),
                dto.getCity()
                );
    }
}