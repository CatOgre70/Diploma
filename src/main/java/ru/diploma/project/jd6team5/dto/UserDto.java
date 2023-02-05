package ru.diploma.project.jd6team5.dto;

import ru.diploma.project.jd6team5.constants.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String firstName;
    private Long id;
    private String lastName;
    private String phone;
    private LocalDateTime regDate;
    private String city;
    private String image;
}