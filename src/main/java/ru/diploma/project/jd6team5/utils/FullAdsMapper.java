package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.service.UserService;

@Component
public class FullAdsMapper {
    private final UserService userService;

    public FullAdsMapper(UserService userService) {
        this.userService = userService;
    }

    public FullAdsDto entityToDto(Ads ads) {
        User user = userService.getUserByID(ads.getUserID());
        String str = "/ads/{adsId}/getimage";
        str = str.replace("{adsId}", ads.getId().toString());

/*        String str = ads.getImage();
        if(str != null) {
            str = str.substring(19);
            str = str.replace('\\', '/');
            str = str.replace(" ", "%20");
        }

 */
        return new FullAdsDto(
                user.getFirstName(),
                user.getLastName(),
                ads.getDescription(),
                user.getEmail(),
                str,
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
