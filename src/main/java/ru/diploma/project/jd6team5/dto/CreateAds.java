package ru.diploma.project.jd6team5.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class CreateAds {
    private String description;
    private Float price;
    private String title;
}
