package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diploma.project.jd6team5.model.AdsImages;

import java.util.Optional;

public interface AdsImagesRepository extends JpaRepository<AdsImages, Long> {
    Optional<AdsImages> getAdsImagesByIdAndImageID(Long imageListId, Long imageId);
}
