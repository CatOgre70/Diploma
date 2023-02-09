package ru.diploma.project.jd6team5.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdsDto {
    private Integer author;
    private byte[] image;
    private Integer pk;
    private int price;
    private String title;
}
