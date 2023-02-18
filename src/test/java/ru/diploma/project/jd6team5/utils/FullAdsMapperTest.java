package ru.diploma.project.jd6team5.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.repository.UserRepository;
import ru.diploma.project.jd6team5.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
class FullAdsMapperTest {

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepo;
    @Spy
    @InjectMocks
    private FullAdsMapper mapper;
    private Ads ads;
    private FullAdsDto dto;
    private User user;

    @BeforeEach
    void init() {
        ads = new Ads(1L, 11L, "title", "desc", 20f, "/ads/1/getimage");
        user = new User(11L, "email", "Name", "lastName", "username", "password", "phone", null, null, null, null);
        dto = new FullAdsDto("Name", "lastName", "desc", "email", "/ads/1/getimage", "phone", 1L, 20, "title");
    }
    @Test
    void entityToDto() {
        when(userService.getUserByID(ads.getUserID())).thenReturn(user);
        FullAdsDto testDto = mapper.entityToDto(ads);
        Assertions.assertEquals(testDto, dto);
    }

    @Test
    void dtoToEntity() {
        Ads testAds = mapper.dtoToEntity(dto, user.getUserID());
        ads.setImage(null);
        Assertions.assertEquals(testAds, ads);
    }
}