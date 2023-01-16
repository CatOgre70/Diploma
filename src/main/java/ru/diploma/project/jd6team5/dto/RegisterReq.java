package ru.diploma.project.jd6team5.dto;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "register_req")
@Getter
@Setter
public class RegisterReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID = 1L;
    private String username = "user@gmail.com";
    private String password = "password";
    @Column(name = "first_name")
    private String firstName = "Имя пользователь";
    @Column(name = "last_name")
    private String lastName = "Отчество пользователя";
    private String phone = "74951234567";
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
}
