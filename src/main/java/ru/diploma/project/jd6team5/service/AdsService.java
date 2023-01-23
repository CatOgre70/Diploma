package ru.diploma.project.jd6team5.service;

import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.dto.CreateAds;
import ru.diploma.project.jd6team5.dto.ResponseWrapperAds;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.repository.AdsRepository;
import ru.diploma.project.jd6team5.utils.AdsMapper;
import ru.diploma.project.jd6team5.utils.FullAdsMapper;

import java.util.List;

/**
 * Класс описывающий логику получения и обработки информации по сущности объявление
 */
@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final FullAdsMapper fullAdsMapper;
    private final AdsMapper compactMapper;

    public AdsService(AdsRepository adsRepository, FullAdsMapper fullAdsMapper, AdsMapper compactMapper) {
        this.adsRepository = adsRepository;
        this.fullAdsMapper = fullAdsMapper;
        this.compactMapper = compactMapper;
    }

    public AdsDto createAds(CreateAds createAds) {
        Ads newAds = new Ads();
        newAds.setDescription(createAds.getDescription());
        newAds.setPrice(createAds.getPrice());
        newAds.setTitle(createAds.getTitle());
        return compactMapper.entityToDto(adsRepository.save(newAds));
    }
    public FullAdsDto findFullAds(long id) {
        return fullAdsMapper.entityToDto(adsRepository.findById(id).orElseThrow(AdsNotFoundException::new));
    }
    public AdsDto updateAds(long id, CreateAds targetAds) {
        Ads newAds = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        newAds.setDescription(targetAds.getDescription());
        newAds.setPrice(targetAds.getPrice());
        newAds.setTitle(targetAds.getTitle());
        return compactMapper.entityToDto(adsRepository.save(newAds));
    }
    public void deleteAds(long id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        adsRepository.delete(ads);
    }

    public ResponseWrapperAds getAllAds() {
        List<Ads> foundAds = adsRepository.findAll();
        ResponseWrapperAds result = new ResponseWrapperAds();
        result.setCount(foundAds.size());
        result.setResults(foundAds);
        return result;
    }

    public ResponseWrapperAds getAllAdsById(long id) {
        List<Ads> foundAds = adsRepository.findAllById(id);
        return new ResponseWrapperAds(foundAds.size(), foundAds);
    }
}
