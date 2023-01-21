package ru.diploma.project.jd6team5.service;

import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.dto.RegisterReq;
import ru.diploma.project.jd6team5.dto.User;
import ru.diploma.project.jd6team5.exception.BadPasswordException;
import ru.diploma.project.jd6team5.exception.NewPasswordAlreadyUsedException;
import ru.diploma.project.jd6team5.exception.RegisterReqNotFoundException;
import ru.diploma.project.jd6team5.exception.UserNotFoundException;
import ru.diploma.project.jd6team5.model.NewPassword;
import ru.diploma.project.jd6team5.repository.RegisterReqRepository;
import ru.diploma.project.jd6team5.repository.UserRepository;

/**
 * Класс описывающий логику получения и обработки информации по сущности Пользователь
 */
@Service
public class UserService {
    private final UserRepository userRepo;
    private final RegisterReqRepository regReqRepo;
    /** Конструктор */
    public UserService(UserRepository userRepo,
                       RegisterReqRepository regReqRepo) {
        this.userRepo = userRepo;
        this.regReqRepo = regReqRepo;
    }

    /**
     * Метод, который выводит информацию о Пользователе
     * @param userID
     * @return представление Пользователя
     */
    public User getUserByID(Long userID) {
        User userFound = userRepo.findUserByID(userID).orElse(null);
        if (userFound == null){
            throw new UserNotFoundException("User not found!");
        }
        return userFound;
    }

    /**
     * Метод, который вносит изменения в информацию Пользователя
     * @param userID
     * @param newPassword
     * @return
     */
    public User updatePassword(Long userID, NewPassword newPassword){
        User userFound = getUserByID(userID);
        RegisterReq regReq = regReqRepo.getRegisterReqByUserID(userFound.getUserID()).orElse(null);
        if (regReq == null){
            throw new RegisterReqNotFoundException("У Пользователя не нашлось Регистрации");
        } else if (newPassword.getNewPassword().equals(regReq.getPassword())){
            throw new NewPasswordAlreadyUsedException("Пароль совпадает с текущим");
        }
        regReq.setPassword(newPassword.getNewPassword());
        regReqRepo.save(regReq);
        return userFound;
    }

    public User updateUser(User inpUser) {
        User userFound = getUserByID(inpUser.getUserID());
        userFound.setEmail(inpUser.getEmail());
        userFound.setAvatarPath(inpUser.getAvatarPath());
        userFound.setFirstName(inpUser.getFirstName());
        userFound.setLastName(inpUser.getLastName());
        userFound.setPhone(inpUser.getPhone());
        userFound.setRegDate(inpUser.getRegDate());
        userFound.setRole(inpUser.getRole());
        userRepo.save(userFound);
        return userFound;
    }
}
