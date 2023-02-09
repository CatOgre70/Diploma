package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.User;

@Component
public class UserMapper {

    public UserDto entityToDto (User user) {
        return new UserDto(
                user.getEmail(),
                user.getFirstName(),
                user.getUserID(),
                user.getLastName(),
                user.getPhone(),
                user.getRegDate(),
                "Город", //TODO: user.getCity(),
                user.getAvatar()
        );
    }

    public User dtoToUser(UserDto dto) {
        return new User(dto.getId(),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(), //TODO: что-то ещё
                null, //TODO: как-то заполнять пароль
                dto.getPhone(),
                UserRole.USER,
                dto.getImage(),
                dto.getRegDate(),
                dto.getCity()
                );
    }
}