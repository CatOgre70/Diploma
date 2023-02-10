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
    @Column(name = "avatar")
    @Lob
    @Type(type="org.hibernate.type.ImageType")
    private byte[] avatarPath;
    @Column(name = "reg_date")
    private LocalDateTime regDate;
/*
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public byte[] getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(byte[] avatarPath) {
        this.avatarPath = avatarPath;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUserID(), user.getUserID()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getPhone(), user.getPhone()) && getRole() == user.getRole() && Arrays.equals(getAvatarPath(), user.getAvatarPath()) && Objects.equals(getRegDate(), user.getRegDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getPhone());
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", avatarPath=" + Arrays.toString(avatarPath) +
                ", regDate=" + regDate +
                '}';
    }*/
}
