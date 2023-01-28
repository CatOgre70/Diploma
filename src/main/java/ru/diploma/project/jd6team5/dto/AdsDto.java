package ru.diploma.project.jd6team5.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdsDto {
    private Long userID;
    private Long imageListID;
    private Long id;
    private Float price;
    private String title;
}
