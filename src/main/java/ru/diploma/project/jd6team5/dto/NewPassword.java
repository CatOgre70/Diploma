package ru.diploma.project.jd6team5.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
