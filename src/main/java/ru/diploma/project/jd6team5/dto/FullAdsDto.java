package ru.diploma.project.jd6team5.dto;

import lombok.*;
import ru.diploma.project.jd6team5.constants.AdsStatus;
import ru.diploma.project.jd6team5.constants.CurrencyCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FullAdsDto {

    public interface UpdateByUser {
    }
    private Long id;
    private Long userID;
    private String authorFirstName;
    private String authorLastName;
    private String title;
    private String description;
    private Float price;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;
    @Enumerated(EnumType.STRING)
    private AdsStatus status;
    private Long imageListID;

}
