package ru.diploma.project.jd6team5.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AdsDto {
    private Long userID;
    private Long imageListID;
    private Long id;
    private Float price;
    private String title;
}
