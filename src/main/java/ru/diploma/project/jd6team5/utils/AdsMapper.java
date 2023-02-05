package ru.diploma.project.jd6team5.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.AdsImages;
import ru.diploma.project.jd6team5.repository.AdsImagesRepository;

import java.util.List;

@Component
public class AdsMapper {

    private final AdsImagesRepository adsImagesRepository;

    private final Logger logger = LoggerFactory.getLogger(AdsMapper.class);

    public AdsMapper(AdsImagesRepository adsImagesRepository) {
        this.adsImagesRepository = adsImagesRepository;
    }

    public AdsDto entityToDto (Ads ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(ads.getId().intValue());
        adsDto.setAuthor(ads.getUserID().intValue());
        adsDto.setTitle(ads.getTitle());
        adsDto.setPrice(adsDto.getPrice());
        List<AdsImages> adsImages = adsImagesRepository.getAllImagesByAdsId(ads.getId().intValue());
        String[] imagesList = new String[0];
        if(!adsImages.isEmpty()) {
            imagesList = new String[adsImages.size()];
            for (int i = 0; i < imagesList.length; i++) {
                imagesList[i] = adsImages.get(i).getImagePath();
            }
        }
        adsDto.setImage(imagesList);
        return adsDto;
    }

    public Ads dtoToAds(AdsDto dto) {
        Ads ads = new Ads();
        ads.setId(dto.getPk().longValue());
        ads.setPrice(dto.getPrice());
        ads.setTitle(dto.getTitle());
        List<AdsImages> adsImages = adsImagesRepository.getAllImagesByAdsId(dto.getPk());
        if(adsImages.size() != dto.getImage().length) {
            logger.error("Images lists in AdsDto and Ads are not equals");
            throw new RuntimeException("Images lists in AdsDto and Ads are not equals");
        }
        for(int i = 0; i < dto.getImage().length; i++) {
            if(!adsImages.get(i).equals(dto.getImage()[i])) {
                logger.error("Images lists in AdsDto and Ads are not equals");
                throw new RuntimeException("Images lists in AdsDto and Ads are not equals");
            }
        }
        return ads;
    }
}
