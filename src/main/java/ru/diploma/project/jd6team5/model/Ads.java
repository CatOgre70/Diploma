package ru.diploma.project.jd6team5.model;

import javax.persistence.*;

import lombok.*;
import ru.diploma.project.jd6team5.constants.AdsStatus;
import ru.diploma.project.jd6team5.constants.CurrencyCode;

@Entity(name = "ads")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID = 1L;
    private String title = "Набор авто-инструментов фирмы \"Автотехник\"";
    private String description = "Набор ручного инструмента предназначенный для ремонта автотехники";
    private Float price = 5000.67f;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency = CurrencyCode.RUB;
    @Enumerated(EnumType.STRING)
    private AdsStatus status = AdsStatus.ACTIVE;
    @Column(name = "image_list_id")
    private Long imageListID;

    public Ads(Long id, Long userID, String title, Float price, Long imageListID) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.price = price;
        this.imageListID = imageListID;
    }
}
