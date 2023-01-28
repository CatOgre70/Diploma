package ru.diploma.project.jd6team5.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.NewPassword;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.exception.ImageFileNotFoundException;
import ru.diploma.project.jd6team5.exception.NewPasswordAlreadyUsedException;
import ru.diploma.project.jd6team5.exception.RegisterReqNotFoundException;
import ru.diploma.project.jd6team5.exception.UserNotFoundException;
import ru.diploma.project.jd6team5.model.RegisterReq;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.repository.RegisterReqRepository;
import ru.diploma.project.jd6team5.repository.UserRepository;
import ru.diploma.project.jd6team5.utils.UserMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Класс описывающий логику получения и обработки информации по сущности Пользователь
 */
@Service
public class UserService {
    private final UserRepository userRepo;
    private final RegisterReqRepository regReqRepo;
    private final UserMapper userMapper;
    @Value("${users.avatars.dir.path}")
    private String targetAvatarDir;
    /** Конструктор */
    public UserService(UserRepository userRepo,
                       RegisterReqRepository regReqRepo,
                       UserMapper userMapper) {
        this.userRepo = userRepo;
        this.regReqRepo = regReqRepo;
        this.userMapper = userMapper;
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
        RegisterReq regReq = regReqRepo.getRegisterReqByUserID(userFound.getUserID()).orElse(null);
        if (regReq == null){
            throw new RegisterReqNotFoundException("У Пользователя не нашлось Регистрации");
        } else if (newPassword.getNewPassword().equals(regReq.getPassword())){
            throw new NewPasswordAlreadyUsedException("Пароль совпадает с текущим");
        }
        regReq.setPassword(newPassword.getNewPassword());
        regReqRepo.save(regReq);
        return userMapper.entityToDto(userFound);
    }

    public UserDto updateUser(UserDto inpUserDto) {
        User userFound = getUserByID(inpUserDto.getUserID());
        userFound.setEmail(inpUserDto.getEmail());
        userFound.setAvatarPath(inpUserDto.getAvatarPath());
        userFound.setFirstName(inpUserDto.getFirstName());
        userFound.setLastName(inpUserDto.getLastName());
        userFound.setPhone(inpUserDto.getPhone());
        userFound.setRegDate(inpUserDto.getRegDate());
        userFound.setRole(inpUserDto.getRole());
        userRepo.save(userFound);
        return userMapper.entityToDto(userFound);
    }

    public void updateUserAvatar(Long userID, MultipartFile inpPicture) throws IOException {
        User userFound = getUserByID(userID);
        Path imagePath = Path.of(targetAvatarDir + "/avatar" + userID + getExtensionOfFile(inpPicture.getOriginalFilename()));
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);
        // Создание потоков и вызов метода передачи данных по 1-му килобайту
        try (InputStream inpStream = inpPicture.getInputStream();
             OutputStream outStream = Files.newOutputStream(imagePath, CREATE_NEW);
             BufferedInputStream bufInpStream = new BufferedInputStream(inpStream, 1024);
             BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream, 1024);
        ) {
            bufInpStream.transferTo(bufOutStream);
        }
        if (Files.exists(imagePath)){
            userFound.setAvatarPath(imagePath.toFile().getPath());
            userRepo.save(userFound);
        } else {
            throw new ImageFileNotFoundException("Не найден файл по указанному пути");
        }
    }
}