package ru.diploma.project.jd6team5.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.model.Ads;

@Component
public class AdsMapper {

    private final Logger logger = LoggerFactory.getLogger(AdsMapper.class);

    public AdsDto entityToDto (Ads ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(ads.getId().intValue());
        adsDto.setAuthor(ads.getUserID().intValue());
        adsDto.setTitle(ads.getTitle());
        adsDto.setPrice(ads.getPrice().intValue());
        String str = "/ads/{adsId}/getimage";
        str = str.replace("{adsId}", ads.getId().toString());
/*        String str = ads.getImage();
        if(str != null) {
            str = str.substring(19);
            str = str.replace('\\', '/');
            str = str.replace(" ", "%20");
        }
 */
        adsDto.setImage(str);
        return adsDto;
    }

    public Ads dtoToAds(AdsDto dto) {
        Ads ads = new Ads();
        ads.setId((long) dto.getPk());
        ads.setPrice((float) dto.getPrice());
        ads.setTitle(dto.getTitle());
        ads.setImage(dto.getImage());
        return ads;
    }
}
