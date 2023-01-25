package ru.diploma.project.jd6team5.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAds {
    private String description;
    private Float price;
    private String title;
}
