package ru.diploma.project.jd6team5.model;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Type;
import ru.diploma.project.jd6team5.constants.UserRole;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @Column(name = "user_id")
    private Long userID;
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String username;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "avatar_path")
    private String avatarPath;
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
