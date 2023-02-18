package ru.diploma.project.jd6team5.service;

import liquibase.pro.packaged.A;
import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.C;
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
import org.springframework.boot.test.context.SpringBootTest;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.CreateAds;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.dto.ResponseWrapperAds;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.repository.AdsRepository;
import ru.diploma.project.jd6team5.utils.AdsMapper;
import ru.diploma.project.jd6team5.utils.FullAdsMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
class AdsServiceTest {
    @Mock
    private AdsRepository repository;
    @Mock
    private AdsMapper compactMapper;
    @Mock
    private FullAdsMapper fullAdsMapper;
    @Spy
    @InjectMocks
    private AdsService adsService;

    private Ads ads;
    private AdsDto dto;
    private FullAdsDto fullAds;
    private CreateAds createAds;
    private ResponseWrapperAds wrapperAds;
    @BeforeEach
    void init() {
        ads = new Ads(1L, 11L, "title", "desc", 20f, "/ads/1/getimage");
        dto = new AdsDto(11, "/ads/1/getimage", 1, 20, "title");
        fullAds = new FullAdsDto("Name", "lastName", "desc", "email", "/ads/1/getimage", "phone", 1L, 20, "title");
        createAds = new CreateAds("desc", 20, "title");
    }

    @Test
    void findFullAds() {
        when(fullAdsMapper.entityToDto(ads)).thenReturn(fullAds);
        when(repository.findById(ads.getId())).thenReturn(Optional.of(ads));
        Assertions.assertEquals(adsService.findFullAds(ads.getId()), fullAds);

        when(repository.findById(ads.getId())).thenReturn(null);
        repository.findById(ads.getId());
        Assertions.assertThrows(AdsNotFoundException.class, () -> {throw new AdsNotFoundException();});

    }

    @Test
    void updateAds() {
        ads.setPrice(1f);
        when(repository.findById(ads.getId())).thenReturn(Optional.of(ads));
        when(compactMapper.entityToDto(ads)).thenReturn(dto);
        when(repository.save(any(Ads.class))).thenReturn(ads);
        Assertions.assertEquals(dto, adsService.updateAds(ads.getId(), createAds));
        when(repository.findById(ads.getId())).thenReturn(null);
        Assertions.assertThrows(AdsNotFoundException.class, () -> {throw new AdsNotFoundException();});
    }

    @Test
    void deleteAds() {
        when(repository.findById(ads.getId())).thenReturn(Optional.of(ads));
        adsService.deleteAds(ads.getId());
        verify(repository).delete(any());
        when(repository.findById(ads.getId())).thenReturn(null);
        Assertions.assertThrows(AdsNotFoundException.class, () -> {throw new AdsNotFoundException();});
    }

    @Test
    void getAllAds() {
        wrapperAds = new ResponseWrapperAds();
        List<AdsDto> dtoList = new ArrayList<>();
        dtoList.add(dto);
        wrapperAds.setResults(dtoList);
        wrapperAds.setCount(dtoList.size());
        when(compactMapper.entityToDto(ads)).thenReturn(dto);
        when(repository.findAll()).thenReturn(new ArrayList<>(List.of(ads)));
        Assertions.assertEquals(wrapperAds, adsService.getAllAds());
    }


    @Test
    void getAllAdsByUserId() {
        wrapperAds = new ResponseWrapperAds();
        List<AdsDto> dtoList = new ArrayList<>();
        dtoList.add(dto);
        wrapperAds.setResults(dtoList);
        wrapperAds.setCount(dtoList.size());
        when(compactMapper.entityToDto(ads)).thenReturn(dto);
        when(repository.findAllByUserID(1L)).thenReturn(new ArrayList<>(List.of(ads)));
        Assertions.assertEquals(wrapperAds, adsService.getAllAdsByUserId(1L));
    }

    @Test
    void findById() {
        when(repository.findById(ads.getId())).thenReturn(Optional.of(ads));
        Assertions.assertEquals(Optional.of(ads), adsService.findById(ads.getId()));
    }
}