package ru.diploma.project.jd6team5.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.exception.UserNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.repository.UserRepository;
import ru.diploma.project.jd6team5.utils.UserMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder passEncoder;
    @Spy
    @InjectMocks
    private UserService service;
    private User user;
    private UserDto dto;

    @BeforeEach
    void setUp() {
        user = new User(11L, "email", "Name", "lastName", "email", "password", "phone", UserRole.USER, "/users/11/getavatar", null, "Moscow");
        dto = new UserDto("email", "Name", 11L, "lastName", "phone", null,  "Moscow", "/users/11/getavatar");
    }

    @Test
    void getUserByID() {
        when(mapper.entityToDto(user)).thenReturn(dto);
        when(repository.findUserByUserID(user.getUserID())).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, service.getUserByID(user.getUserID()));
        when(repository.findUserByUserID(user.getUserID())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> {throw new UserNotFoundException();});
    }

    @Test
    void getUserDto() {
        when(mapper.entityToDto(user)).thenReturn(dto);
        Assertions.assertEquals(dto, service.getUserDto(user));
    }

    @Test
    void updateUser() {
        user.setFirstName("UpdatedName");
        when(repository.findUserByEmail(any())).thenReturn(Optional.of(user));
        when(repository.findUserByUserID(any())).thenReturn(Optional.of(user));
        when(mapper.entityToDto(user)).thenReturn(dto);
        when(repository.save(any(User.class))).thenReturn(user);
        Assertions.assertEquals(dto, service.updateUser(dto, user.getUsername()));
    }


    @Test
    void getUserIdByName() {
        when(repository.findUserByEmail(any())).thenReturn(Optional.of(user));
        Assertions.assertEquals(user.getUserID(), service.getUserIdByName(user.getEmail()));
        when(repository.findUserByEmail(any())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> {throw new UserNotFoundException();});
    }

    @Test
    void getUserByName() {
        when(repository.findUserByEmail(any())).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, service.getUserByName(user.getEmail()));
        when(repository.findUserByEmail(any())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> {throw new UserNotFoundException();});
    }
}