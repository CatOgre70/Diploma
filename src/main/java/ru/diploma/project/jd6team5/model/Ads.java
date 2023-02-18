package ru.diploma.project.jd6team5.model;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Type;
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
    private Float price;
    private String image;

    public Ads(Long id, Long userID, String title, String description, Float price) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}
