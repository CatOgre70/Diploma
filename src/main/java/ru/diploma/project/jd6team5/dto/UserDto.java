package ru.diploma.project.jd6team5.dto;

import ru.diploma.project.jd6team5.constants.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userID;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phone;
    private UserRole role;
    private String avatarPath;
    private LocalDateTime regDate;
}