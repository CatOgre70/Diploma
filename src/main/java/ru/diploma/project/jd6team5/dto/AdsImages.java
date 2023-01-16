package ru.diploma.project.jd6team5.dto;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "ads_images")
@Getter
@Setter
public class AdsImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_id")
    private Long imageID;
    @Column(name = "image_path")
    private String imagePath;
}
