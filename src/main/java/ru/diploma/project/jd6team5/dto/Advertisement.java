package ru.diploma.project.jd6team5.dto;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "ads")
@Getter
@Setter
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID = 1L;
    private String label = "Набор авто-инструментов фирмы \"Автотехник\"";
    private String description = "Набор ручного инструмента предназначенный для ремонта автотехники";
    private Float price = 5000.67f;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency = CurrencyCode.RUB;
    @Enumerated(EnumType.STRING)
    private AdsStatus status = AdsStatus.ACTIVE;
    @Column(name = "image_list_id")
    private Long imageListID;
}
