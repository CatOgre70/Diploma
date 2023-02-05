package ru.diploma.project.jd6team5.model;

import javax.persistence.*;

import lombok.*;
import ru.diploma.project.jd6team5.constants.AdsStatus;
import ru.diploma.project.jd6team5.constants.CurrencyCode;

@Entity(name = "ads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID;
    private String title;
    private String description;
    private float price;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;
    @Enumerated(EnumType.STRING)
    private AdsStatus status;
    @Column(name = "image_list_id")
    private Long imageListID;
}
