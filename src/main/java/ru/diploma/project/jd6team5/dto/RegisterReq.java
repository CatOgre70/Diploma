package ru.diploma.project.jd6team5.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "register_req")
@Getter
@Setter
public class RegisterReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private String username;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
