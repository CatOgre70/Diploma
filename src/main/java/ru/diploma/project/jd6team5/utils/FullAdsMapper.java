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
        return new FullAdsDto(ads.getId(),
                ads.getUserID(),
                user.getFirstName(),
                user.getLastName(),
                ads.getTitle(),
                ads.getDescription(),
                ads.getPrice(),
                user.getEmail(),
                user.getPhone(),
                ads.getCurrency(),
                ads.getStatus(),
                ads.getImageListID());
    }

    public Ads dtoToEntity(FullAdsDto dto) {
        return new Ads(dto.getId(),
                dto.getUserID(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getCurrency(),
                dto.getStatus(),
                dto.getImageListID());
    }
}
