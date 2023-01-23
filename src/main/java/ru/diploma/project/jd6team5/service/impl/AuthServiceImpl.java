package ru.diploma.project.jd6team5.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.model.RegisterReq;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager userDM;
    private final PasswordEncoder passEnc;

    public AuthServiceImpl(UserDetailsManager userDM) {
        this.userDM = userDM;
        this.passEnc = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        if (!userDM.userExists(userName)) {
            return false;
        }
        UserDetails userD = userDM.loadUserByUsername(userName);
        String encryptPwd = userD.getPassword();
        String encryptPwdWithoutType = encryptPwd.substring(8);
        return passEnc.matches(encryptPwd, encryptPwdWithoutType);
    }

    @Override
    public boolean register(RegisterReq registerReq, UserRole role) {
        if (userDM.userExists(registerReq.getUsername())) {
            return false;
        }
        userDM.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );
        return true;
    }
}
