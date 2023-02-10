package ru.diploma.project.jd6team5.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.*;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.repository.AdsRepository;
import ru.diploma.project.jd6team5.utils.AdsMapper;
import ru.diploma.project.jd6team5.utils.FullAdsMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс описывающий логику получения и обработки информации по сущности объявление
 */
@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final FullAdsMapper fullAdsMapper;
    private final AdsMapper compactMapper;
    @Value("${ads.images.dir.path}")
    private String targetImagesDir;

    public AdsService(AdsRepository adsRepository, FullAdsMapper fullAdsMapper,
                      AdsMapper compactMapper) {
        this.adsRepository = adsRepository;
        this.fullAdsMapper = fullAdsMapper;
        this.compactMapper = compactMapper;
    }

    public AdsDto createAds(Long userID, CreateAds createAds, MultipartFile inpPicture) {
        Ads newAds = new Ads();
        newAds.setUserID(userID);
        newAds.setDescription(createAds.getDescription());
        newAds.setPrice((float)createAds.getPrice());
        newAds.setTitle(createAds.getTitle());
        Ads createdAds = adsRepository.save(newAds);
        try {
            byte[] image = inpPicture.getBytes();
            createdAds.setImage(image);
            createdAds = adsRepository.save(createdAds);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return compactMapper.entityToDto(createdAds);
    }

    public FullAdsDto findFullAds(Long id) {
        Ads adsFound = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return fullAdsMapper.entityToDto(adsFound);
    }
    public AdsDto updateAds(Long id, CreateAds targetAds) {
        Ads newAds = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        newAds.setDescription(targetAds.getDescription());
        newAds.setPrice((float) targetAds.getPrice());
        newAds.setTitle(targetAds.getTitle());
        return compactMapper.entityToDto(adsRepository.save(newAds));
    }
    public void deleteAds(Long id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        adsRepository.delete(ads);
    }

    public ResponseWrapperAds getAllAds() {
        List<Ads> foundAds = adsRepository.findAll();
        List<AdsDto> foundAdsDto = new ArrayList<>(foundAds.size());
        for (Ads a : foundAds) {
            foundAdsDto.add(compactMapper.entityToDto(a));
        }
        ResponseWrapperAds result = new ResponseWrapperAds();
        result.setCount(foundAds.size());
        result.setResults(foundAdsDto);
        return result;
    }


    public ResponseWrapperAds getAllAdsById(Long id) {
        List<Ads> foundAds = adsRepository.findAllById(id);
        List<AdsDto> foundAdsDto;
        if(foundAds.isEmpty()) {
            foundAdsDto = Collections.emptyList();
        } else {
            foundAdsDto = new ArrayList<>(foundAds.size());
            for (Ads a : foundAds) {
                foundAdsDto.add(compactMapper.entityToDto(a));
            }
        }
        ResponseWrapperAds response = new ResponseWrapperAds();
        response.setCount(foundAds.size());
        response.setResults(foundAdsDto);
        return response;
    }

    public ResponseWrapperAds getAllAdsByUserId(Long userID) {
        List<Ads> foundAds = adsRepository.findAllByUserID(userID);
        List<AdsDto> foundAdsDto;
        if(foundAds.isEmpty()) {
            foundAdsDto = Collections.emptyList();
        } else {
            foundAdsDto = new ArrayList<>(foundAds.size());
            for (Ads a : foundAds) {
                foundAdsDto.add(compactMapper.entityToDto(a));
            }
        }
        ResponseWrapperAds response = new ResponseWrapperAds();
        response.setCount(foundAds.size());
        response.setResults(foundAdsDto);
        return response;
    }

    private Ads saveIncomeImage(Long adsId, MultipartFile inpPicture) throws IOException {
        Ads ads = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        try {
            byte[] bytes = inpPicture.getBytes();
            ads.setImage(bytes);
            ads = adsRepository.save(ads);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        return ads;
    }

    public byte[] updateAndGetImage(Long adsId, MultipartFile inpPicture) throws IOException {
        Ads adsFound = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        adsFound = saveIncomeImage(adsId, inpPicture);
        return adsFound.getImage();
    }

    private String getExtensionOfFile(String inpPath){
        if (inpPath.contains(".")){
            return inpPath.substring(inpPath.lastIndexOf("."), inpPath.length());
        } else {
            return "";
        }
    }
}
