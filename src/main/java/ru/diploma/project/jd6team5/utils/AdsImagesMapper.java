package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.AdsImagesDto;
import ru.diploma.project.jd6team5.model.AdsImage;

@Component
public class AdsImagesMapper {
    public AdsImagesDto entityToDto (AdsImage adsImage) {
        return new AdsImagesDto(adsImage.getId(),
                adsImage.getAdsId().intValue(),
                adsImage.getImagePath());
    }

    public AdsImage dtoToUser(AdsImagesDto dto) {
        return new AdsImage(dto.getId(),
                (long) dto.getAdsId(),
                dto.getImagePath());
    }
}
