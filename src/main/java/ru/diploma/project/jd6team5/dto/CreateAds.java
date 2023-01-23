package ru.diploma.project.jd6team5.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreateAds {
    private String description;
    private Float price;
    private String title;
}
