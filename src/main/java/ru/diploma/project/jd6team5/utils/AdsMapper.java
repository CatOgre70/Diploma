package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.model.Ads;

@Component
public class AdsMapper {

    public AdsDto entityToDto (Ads ads) {
        return new AdsDto(ads.getUserID(),
                ads.getImageListID(),
                ads.getId(),
                ads.getPrice().intValue(),
                ads.getTitle());
    }

    public Ads dtoToAds(AdsDto dto) {
        return new Ads(dto.getPk(),
                dto.getAuthor(),
                dto.getTitle(),
                null,
                dto.getPrice().floatValue(),
                null,
                null,
                dto.getImage()
        );
    }
}
