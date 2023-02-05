package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diploma.project.jd6team5.model.AdsImage;

import java.util.List;
import java.util.Optional;

public interface AdsImagesRepository extends JpaRepository<AdsImage, Long> {
    List<AdsImage> findAdsImageByAdsId(Long adsId);
}
