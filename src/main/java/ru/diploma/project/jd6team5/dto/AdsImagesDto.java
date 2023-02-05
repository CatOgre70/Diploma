package ru.diploma.project.jd6team5.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdsImagesDto {
    private Long id;
    private int adsId;
    private String imagePath;
}
