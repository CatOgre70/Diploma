package ru.diploma.project.jd6team5.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdsDto {
    private Long author;
    private Long image;
    private Long pk;
    private Integer price;
    private String title;
}
