package ru.diploma.project.jd6team5.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "login_req")
@Getter
@Setter
public class LoginReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public LoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginReq)) return false;
        LoginReq loginReq = (LoginReq) o;
        return Objects.equals(getUsername(), loginReq.getUsername()) && Objects.equals(getPassword(), loginReq.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
