package ru.diploma.project.jd6team5.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "ads")
@Getter
@Setter
public class Ads {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User author;
    @OneToOne
    private AdsImages image;
    private String description;
    private String pk;
    private int price;
    private String title;
}
