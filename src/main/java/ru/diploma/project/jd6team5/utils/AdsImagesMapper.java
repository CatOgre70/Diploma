package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.AdsImagesDto;
import ru.diploma.project.jd6team5.model.AdsImages;

@Component
public class AdsImagesMapper {
    public AdsImagesDto entityToDto (AdsImages images) {
        return new AdsImagesDto(images.getId(),
                images.getImageID(),
                images.getImagePath());
    }

    public AdsImages dtoToUser(AdsImagesDto dto) {
        return new AdsImages(dto.getId(),
                dto.getImageID(),
                dto.getImagePath());
    }
}
