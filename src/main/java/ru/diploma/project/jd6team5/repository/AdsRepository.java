package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.diploma.project.jd6team5.model.Ads;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    List<Ads> findAllById(long id);
}
