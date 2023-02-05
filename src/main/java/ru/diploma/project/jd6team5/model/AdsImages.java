package ru.diploma.project.jd6team5.model;

import javax.persistence.*;

import lombok.*;

@Entity(name = "ads_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ads_id")
    private Long adsId;
    @Column(name = "image_path")
    private String imagePath;
}
