package ru.diploma.project.jd6team5.dto;

import lombok.*;
import ru.diploma.project.jd6team5.constants.AdsStatus;
import ru.diploma.project.jd6team5.constants.CurrencyCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FullAdsDto {

    public interface UpdateByUser {
    }
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Long pk;
    private Integer price;
    private String title;
}
