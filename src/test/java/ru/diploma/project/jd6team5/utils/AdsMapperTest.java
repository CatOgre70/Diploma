package ru.diploma.project.jd6team5.utils;

import io.swagger.v3.oas.annotations.servers.Server;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.model.Ads;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdsMapperTest {
    @Autowired
    private AdsMapper mapper;
    private Ads ads;
    private AdsDto dto;

    @BeforeEach
    void init() {
        ads = new Ads(1L, 11L, "title", "desc", 20f, "/ads/1/getimage");
        dto = new AdsDto(11, "/ads/1/getimage", 1, 20, "title");
    }
    @Test
    void entityToDto() {
        AdsDto testDto = mapper.entityToDto(ads);
        Assertions.assertEquals(testDto, dto);
    }

    @Test
    void dtoToAds() {
        Ads testAds = mapper.dtoToAds(dto);
        ads.setDescription(null);
        ads.setUserID(null);
        Assertions.assertEquals(testAds, ads);
    }
}