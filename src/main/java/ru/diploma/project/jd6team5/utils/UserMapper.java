package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.User;

@Component
public class UserMapper {

    public UserDto entityToDto (User user) {
        return new UserDto(user.getUserID(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getPhone(),
				user.getRole(),
				user.getAvatarPath(),
				user.getRegDate());
    }

    public User dtoToUser(UserDto dto) {
        return new User(dto.getUserID(),
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getPhone(),
				dto.getRole(),
				dto.getAvatarPath(),
				dto.getRegDate());
    }
}