package ru.diploma.project.jd6team5.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.AdsImage;
import ru.diploma.project.jd6team5.repository.AdsImagesRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        adsDto.setPrice(ads.getPrice().intValue());
        List<AdsImage> adsImages = adsImagesRepository.findAdsImageByAdsId(ads.getId());
        List<String> imagesList = adsImages.stream().map(AdsImage::getImagePath).toList();
        adsDto.setImage(imagesList);
        return adsDto;
    }

    public Ads dtoToAds(AdsDto dto) {
        Ads ads = new Ads();
        ads.setId((long) dto.getPk());
        ads.setPrice((float) dto.getPrice());
        ads.setTitle(dto.getTitle());
        List<AdsImage> adsImages = adsImagesRepository.findAdsImageByAdsId((long) dto.getPk());
        if(adsImages.size() != dto.getImage().size()) {
            logger.error("Images lists in AdsDto and Ads are not equals");
            throw new RuntimeException("Images lists in AdsDto and Ads are not equals");
        }
        for(int i = 0; i < dto.getImage().size(); i++) {
            if(!adsImages.get(i).equals(dto.getImage().get(i))) {
                logger.error("Images lists in AdsDto and Ads are not equals");
                throw new RuntimeException("Images lists in AdsDto and Ads are not equals");
            }
        }
        return ads;
    }
}
