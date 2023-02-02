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
    private int id;
    @Column(name = "user_id")
    private int userID;
    private String title;
    private String description;
    private int price;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;
    @Enumerated(EnumType.STRING)
    private AdsStatus status;

    public Ads(int id, int userID, String title, int price, Long imageListID) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.price = price;
    }
}
