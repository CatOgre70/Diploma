package ru.diploma.project.jd6team5.dto;

import javax.persistence.*;
import lombok.*;
import ru.diploma.project.jd6team5.constants.UserRole;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegReqDto {
    private Long id;
    private Long userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole role = UserRole.USER;
}