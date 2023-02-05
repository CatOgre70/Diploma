package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class FullAdsMapper {
    private final UserService userService;

    public FullAdsMapper(UserService userService) {
        this.userService = userService;
    }

    public FullAdsDto entityToDto(Ads ads) {
        User user = userService.getUserByID(ads.getUserID());
        return new FullAdsDto(
                user.getFirstName(),
                user.getLastName(),
                ads.getDescription(),
                user.getEmail(),
                new ArrayList<String>(){}, //ads.getImageListID(),
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
                dto.getPrice(),
                null,
                null,
                1L//new ArrayList<String>(){} //dto.getImage()
        );
    }
}
