package ru.diploma.project.jd6team5.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
