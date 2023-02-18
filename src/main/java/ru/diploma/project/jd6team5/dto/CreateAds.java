package ru.diploma.project.jd6team5.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAds {
    private String description;
    private int price;
    private String title;
}
