package ru.diploma.project.jd6team5.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.RegReqDto;
import ru.diploma.project.jd6team5.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserDetailsManager userDM;
    private final PasswordEncoder passEnc;
    private final UserRepository userRepo;

    public AuthService(UserDetailsManager userDM, UserRepository userRepo) {
        this.userDM = userDM;
        this.userRepo = userRepo;
        this.passEnc = new BCryptPasswordEncoder();
    }

    public boolean login(String userName, String password) {
        if (!userDM.userExists(userName)) {
            return false;
        }
        UserDetails userD = userDM.loadUserByUsername(userName);
        String encryptPwd = userD.getPassword();
        String encryptPwdWithoutType = encryptPwd.substring(8);
//        return passEnc.matches(encryptPwd, encryptPwdWithoutType);
        return passEnc.matches(password, encryptPwdWithoutType);
    }

    public boolean register(RegReqDto registerReq, UserRole role) {
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
        ru.diploma.project.jd6team5.model.User userInst = new ru.diploma.project.jd6team5.model.User();
        userInst.setRegDate(LocalDateTime.now());
        userInst.setRole(role);
        userInst.setPhone(registerReq.getPhone());
        userInst.setFirstName(registerReq.getFirstName());
        userInst.setLastName(registerReq.getLastName());
        userInst.setUsername(registerReq.getUsername());
        userInst.setPassword(registerReq.getUsername());
        userInst.setUserID(nextUserID);
        userRepo.save(userInst);
        return true;
    }
}
