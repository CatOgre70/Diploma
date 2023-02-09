package ru.diploma.project.jd6team5.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.NewPassword;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.exception.*;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.repository.UserRepository;
import ru.diploma.project.jd6team5.utils.UserMapper;

import java.io.*;

/**
 * Класс описывающий логику получения и обработки информации по сущности Пользователь
 */
@Service
public class UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    @Value("${users.avatars.dir.path}")
    private String targetAvatarDir;
    private final PasswordEncoder passEnc;
    /** Конструктор */
    public UserService(UserRepository userRepo,
                       UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passEnc = new BCryptPasswordEncoder();
    }

    /**
     * Метод возвращает расширение файла, полученного через входящее значение переменной
     * @param inpPath
     * @return <i><b>пример:</b></i> jpg, bmp, png
     */
    private String getExtensionOfFile(String inpPath){
        if (inpPath.contains(".")){
            return inpPath.substring(inpPath.lastIndexOf("."), inpPath.length());
        } else {
            return "";
        }
    }

    /**
     * Метод, который выводит информацию о Пользователе
     * @param userID
     * @return представление Пользователя
     */
    public User getUserByID(Long userID) {
        User userFound = userRepo.findUserByUserID(userID).orElse(null);
        if (userFound == null){
            throw new UserNotFoundException("User not found!");
        }
        return userFound;
    }

    /**
     * Метод, который выводит информацию о Пользователе (DTO)
     * @param inpUser
     * @return представление Пользователя
     */
    public UserDto getUserDto(User inpUser) {
        return userMapper.entityToDto(inpUser);
    }

    /**
     * Метод, который вносит изменения в информацию Пользователя
     * @param userID
     * @param newPassword
     * @return
     */
    public UserDto updatePassword(Long userID, NewPassword newPassword){
        User userFound = getUserByID(userID);
        String currPass = newPassword.getCurrentPassword() == null ? "EMPTY" : newPassword.getCurrentPassword();
        if (newPassword.getNewPassword() == null){
            throw new BadPasswordException("пароль не может быть пустым!");
        } else if (newPassword.getNewPassword().equals(newPassword.getCurrentPassword())){
            throw new NewPasswordAlreadyUsedException("Пароль совпадает с текущим");
        } else if (!passEnc.matches(currPass, userFound.getPassword())){
            throw new BadPasswordException("Неверно введён текущий пароль!");
        }
        userFound.setPassword(passEnc.encode(newPassword.getNewPassword()));
        return userMapper.entityToDto(userRepo.save(userFound));
    }
    public UserDto updateUser(UserDto inpUserDto) {
        User userFound = getUserByID(inpUserDto.getId());
        userFound.setEmail(inpUserDto.getEmail());
        userFound.setAvatar(inpUserDto.getImage());
        userFound.setFirstName(inpUserDto.getFirstName());
        userFound.setLastName(inpUserDto.getLastName());
        userFound.setPhone(inpUserDto.getPhone());
        userFound.setRegDate(inpUserDto.getRegDate());
        userRepo.save(userFound);
        return userMapper.entityToDto(userFound);
    }

    public byte[] updateUserAvatar(Long userID, MultipartFile inpPicture) throws IOException {
        User userFound = getUserByID(userID);
        try {
            byte[] image = inpPicture.getBytes();
            userFound.setAvatar(image);
            userFound = userRepo.save(userFound);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return userFound.getAvatar();
    }

    public Long getUserIdByName(String name) {
        User user = userRepo.findUserByEmail(name).orElseThrow(UserNotFoundException::new);
        return user.getUserID();
    }
}