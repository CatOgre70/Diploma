package ru.diploma.project.jd6team5.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReqDto {
    private String username;
    private String password;
}
