package ru.diploma.project.jd6team5.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdsDto {
    private int author;
    private String[] image;
    private int pk;
    private int price;
    private String title;
}
