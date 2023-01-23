package ru.diploma.project.jd6team5.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.dto.LoginReq;
import ru.diploma.project.jd6team5.dto.RegisterReq;
import ru.diploma.project.jd6team5.dto.UserRole;
import ru.diploma.project.jd6team5.repository.LoginReqRepository;
import ru.diploma.project.jd6team5.repository.RegisterReqRepository;
import ru.diploma.project.jd6team5.repository.UserRepository;
import ru.diploma.project.jd6team5.service.AuthService;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager userDM;
    private final PasswordEncoder passEnc;
    private final UserRepository userRepo;
    private final RegisterReqRepository regReqRepo;
    private final LoginReqRepository loginReqRepo;

    public AuthServiceImpl(UserDetailsManager userDM, UserRepository userRepo, RegisterReqRepository regReqRepo, LoginReqRepository loginReqRepo) {
        this.userDM = userDM;
        this.userRepo = userRepo;
        this.regReqRepo = regReqRepo;
        this.loginReqRepo = loginReqRepo;
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
        LoginReq login = new LoginReq(userName, password);
        loginReqRepo.save(login);
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
        Long nextUserID = userRepo.count() + 1;
        ru.diploma.project.jd6team5.dto.User userInst = new ru.diploma.project.jd6team5.dto.User();
        userInst.setRegDate(LocalDateTime.now());
        userInst.setRole(role);
        userInst.setPhone(registerReq.getPhone());
        userInst.setFirstName(registerReq.getFirstName());
        userInst.setLastName(registerReq.getLastName());
        userInst.setUserID(nextUserID);
        userRepo.save(userInst);
        registerReq.setUserID(nextUserID);
        regReqRepo.save(registerReq);
        return true;
    }
}
