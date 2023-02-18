package ru.diploma.project.jd6team5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diploma.project.jd6team5.constants.UserRole;

@NoArgsConstructor
@Data
public class RegReqDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole role;
}