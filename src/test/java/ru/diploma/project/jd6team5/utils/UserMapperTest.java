package ru.diploma.project.jd6team5.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;
    private User user;
    private UserDto dto;

    @BeforeEach
    void init() {
        user = new User(11L, "email", "Name", "lastName", "email", "password", "phone", UserRole.USER, "/users/11/getavatar", null, "Moscow");
        dto = new UserDto("email", "Name", 11L, "lastName", "phone", null,  "Moscow", "/users/11/getavatar");
    }
    @Test
    void entityToDto() {
        UserDto testDto = mapper.entityToDto(user);
        Assertions.assertEquals(testDto, dto);
    }

    @Test
    void dtoToUser() {
        User testUser = mapper.dtoToUser(dto);
        user.setPassword(null);
        Assertions.assertEquals(testUser, user);
    }
}