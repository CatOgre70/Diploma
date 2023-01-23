package ru.diploma.project.jd6team5.service;

import ru.diploma.project.jd6team5.model.RegisterReq;
import ru.diploma.project.jd6team5.constants.UserRole;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, UserRole role);
}