package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.model.AdsImage;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.repository.AdsImagesRepository;
import ru.diploma.project.jd6team5.service.UserService;
import java.util.ArrayList;
import java.util.List;

@Component
public class FullAdsMapper {
    private final UserService userService;
    private final AdsImagesRepository adsImageRepo;

    public FullAdsMapper(UserService userService,
                         AdsImagesRepository adsImageRepo) {
        this.userService = userService;
        this.adsImageRepo = adsImageRepo;
    }

    public FullAdsDto entityToDto(Ads ads) {
        User user = userService.getUserByID(ads.getUserID());
        List<AdsImage> adsImageList = adsImageRepo.findAdsImageByAdsId(ads.getId());
        List<String> imageList = adsImageList.stream().map(AdsImage::getImagePath).toList();
        return new FullAdsDto(
                user.getFirstName(),
                user.getLastName(),
                ads.getDescription(),
                user.getEmail(),
                imageList,
                user.getPhone(),
                ads.getId(),
                Math.round(ads.getPrice()),
                ads.getTitle()
        );
    }

    public Ads dtoToEntity(FullAdsDto dto, Long userID) {
        return new Ads(
                dto.getPk(),
                userID,
                dto.getTitle(),
                dto.getDescription(),
                dto.getPrice().floatValue()
        );
    }
}
