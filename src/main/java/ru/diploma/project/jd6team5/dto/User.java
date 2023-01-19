package ru.diploma.project.jd6team5.dto;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "users")
@Getter
@Setter
public class User{
    @Id
    @Column(name = "user_id")
    private Long userID = 1L;
    private String email = "user@mail.com";
    @Column(name = "first_name")
    private String firstName = "Иван";
    @Column(name = "last_name")
    private String lastName = "Петрович";
    private String phone = "84951234567";
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    @Column(name = "avatar_path")
    private String avatarPath;
    @Column(name = "reg_date")
    private LocalDateTime regDate = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUserID(), user.getUserID()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getPhone(), user.getPhone()) && getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getFirstName(), getLastName());
    }
}
