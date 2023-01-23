package ru.diploma.project.jd6team5.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.diploma.project.jd6team5.constants.UserRole;

@Entity(name = "register_req")
@Getter
@Setter
public class RegisterReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID;
    private String username;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
}
