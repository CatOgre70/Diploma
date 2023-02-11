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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

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

    public AdsDto createAds(Long userID, CreateAds createAds, MultipartFile inpPicture) throws IOException {
        Ads newAds = new Ads();
        newAds.setUserID(userID);
        newAds.setDescription(createAds.getDescription());
        newAds.setPrice((float)createAds.getPrice());
        newAds.setTitle(createAds.getTitle());
        Ads createdAds = adsRepository.save(newAds);

        Path imagePath = Path.of(targetImagesDir + "/image_" + createdAds.getId() +
                getExtensionOfFile(inpPicture.getOriginalFilename()));
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);
        // Создание потоков и вызов метода передачи данных по 1-му килобайту
        try (InputStream inpStream = inpPicture.getInputStream();
             OutputStream outStream = Files.newOutputStream(imagePath, CREATE_NEW);
             BufferedInputStream bufInpStream = new BufferedInputStream(inpStream, 1024);
             BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream, 1024);
        ) {
            bufInpStream.transferTo(bufOutStream);
        }
        createdAds.setImage(imagePath.toString());
        createdAds = adsRepository.save(createdAds);
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

    private Path saveIncomeImage(Long adsId, MultipartFile inpPicture) throws IOException {
        Path imagePath = Path.of(targetImagesDir + "/image_" + adsId +
                getExtensionOfFile(inpPicture.getOriginalFilename()));
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);
        // Создание потоков и вызов метода передачи данных по 1-му килобайту
        try (InputStream inpStream = inpPicture.getInputStream();
             OutputStream outStream = Files.newOutputStream(imagePath, CREATE_NEW);
             BufferedInputStream bufInpStream = new BufferedInputStream(inpStream, 1024);
             BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream, 1024);
        ) {
            bufInpStream.transferTo(bufOutStream);
        }
        return imagePath;
    }

    public String updateAndGetImage(Long adsId, MultipartFile inpPicture) throws IOException {
        Ads adsFound = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        Path imagePath = saveIncomeImage(adsFound.getId(), inpPicture);
        String imagePathString = imagePath.toString();
        adsFound.setImage(imagePathString);
        adsRepository.save(adsFound);
        return imagePathString;
    }

    private String getExtensionOfFile(String inpPath){
        if (inpPath.contains(".")){
            return inpPath.substring(inpPath.lastIndexOf("."), inpPath.length());
        } else {
            return "";
        }
    }

    public Optional<Ads> findById(Long adsId) {
        return adsRepository.findById(adsId);
    }
}
