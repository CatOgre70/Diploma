package ru.diploma.project.jd6team5.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

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
     * Метод, который получает из БД Пользователя по его ИД номеру
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
     * Метод, смены пароля Пользователя
     * @param authUserName
     * @param newPassword
     * @return Возвращает DTO Пользователя
     */
    public UserDto updatePassword(String authUserName, NewPassword newPassword){
        Long id = getUserIdByName(authUserName);
        User userFound = getUserByID(id);
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
    /**
     * Метод, который вносит изменения в профиль Пользователя
     * @param authUserName
     * @param inpUserDto
     * @return
     */
    public UserDto updateUser(UserDto inpUserDto, String authUserName) {
        Long id = getUserIdByName(authUserName);
        User userFound = getUserByID(id);
        userFound.setFirstName(inpUserDto.getFirstName());
        userFound.setLastName(inpUserDto.getLastName());
        userFound.setPhone(inpUserDto.getPhone());
        userFound.setRegDate(inpUserDto.getRegDate());
        userRepo.save(userFound);
        return userMapper.entityToDto(userFound);
    }
    /**
     * Метод, который меняет аватар Пользователя
     * @param authUserName
     * @param inpPicture
     */
    public void updateUserAvatar(String authUserName, MultipartFile inpPicture) throws IOException {
        Long id = getUserIdByName(authUserName);
        User userFound = getUserByID(id);
        Path imagePath = Path.of(targetAvatarDir + "/avatar" + id + getExtensionOfFile(inpPicture.getOriginalFilename()));
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);
        // Создание потоков и вызов метода передачи данных по 1-му килобайту
        try (InputStream inpStream = inpPicture.getInputStream();
             OutputStream outStream = Files.newOutputStream(imagePath, CREATE_NEW);
             BufferedInputStream bufInpStream = new BufferedInputStream(inpStream, 1024);
             BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream, 1024);
        ) {
            bufInpStream.transferTo(bufOutStream);
            userFound.setAvatar(imagePath.toString());
        }
        if (Files.exists(imagePath)){
            userRepo.saveAndFlush(userFound);
        } else {
            throw new ImageFileNotFoundException("Не найден файл по указанному пути");
        }
    }
    /**
     * Метод, который получает картинку аватара Пользователя
     *
     * @param id
     * @return
     */
    public byte[] getUserAvatar(Long id) throws IOException {
        User userFound = getUserByID(id);
        if (userFound.getAvatar() != null) {
            Path imagePath = Path.of(userFound.getAvatar());
            return Files.readAllBytes(imagePath);
        } else {
            return null;
        }
    }
    /**
     * Метод, поиска ИД Пользователя по его Логину
     * @param name
     * @return ИД Пользователя
     */
    public Long getUserIdByName(String name) {
        User user = userRepo.findUserByEmail(name).orElseThrow(UserNotFoundException::new);
        return user.getUserID();
    }

    /**
     * Метод, поиска Пользователя по его Логину
     * @param name
     * @return ИД Пользователя
     */
    public User getUserByName(String name) {
        return userRepo.findUserByEmail(name).orElseThrow(UserNotFoundException::new);
    }
}