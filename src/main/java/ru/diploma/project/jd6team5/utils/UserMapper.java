package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.User;

@Component
public class UserMapper {

    public UserDto entityToDto (User user) {
        String avatar = "/users/{id}/getavatar";
        avatar = avatar.replace("{id}", user.getUserID().toString());
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
                dto.getEmail(),
                null,
                dto.getPhone(),
                UserRole.USER,
                avatar,
                dto.getRegDate(),
                dto.getCity()
                );
    }
}