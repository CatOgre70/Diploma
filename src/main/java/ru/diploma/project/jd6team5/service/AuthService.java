package ru.diploma.project.jd6team5.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.RegReqDto;
import ru.diploma.project.jd6team5.exception.UserNotFoundException;
import ru.diploma.project.jd6team5.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    public UserDetails login(String userName, String password) {
        password = password == null ? "EMPTY" : password;
        String encryptPwd;
        String encryptPwdWithoutType;
        if (!userDM.userExists(userName)) {
            return null;
        } else {
            UserDetails userD = userDM.loadUserByUsername(userName);
            encryptPwd = userD.getPassword();
            encryptPwdWithoutType = encryptPwd.substring(8);
            if (passEnc.matches(password, encryptPwdWithoutType)) {
                return userD;
            } else {
                return null;
            }
        }
    }

    public boolean register(RegReqDto registerReq, UserRole role) {
        if (userDM.userExists(registerReq.getUsername())) {
            return false;
        }
        UserDetails instUser = User.withDefaultPasswordEncoder()
                .password(registerReq.getPassword())
                .username(registerReq.getUsername())
                .roles(role.name())
                .build();
        userDM.createUser(instUser);
        ru.diploma.project.jd6team5.model.User userInst = userRepo.getUserByUsername(registerReq.getUsername()).orElse(null);
        if (userInst != null){
            userInst.setRegDate(LocalDateTime.now());
            userInst.setRole(role);
            userInst.setEmail(registerReq.getUsername());
            userInst.setPhone(registerReq.getPhone());
            userInst.setFirstName(registerReq.getFirstName());
            userInst.setLastName(registerReq.getLastName());
            userRepo.save(userInst);
        }
        return true;
    }
}
