package ru.diploma.project.jd6team5.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.*;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.exception.ImageFileNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.AdsImages;
import ru.diploma.project.jd6team5.repository.AdsImagesRepository;
import ru.diploma.project.jd6team5.repository.AdsRepository;
import ru.diploma.project.jd6team5.utils.AdsImagesMapper;
import ru.diploma.project.jd6team5.utils.AdsMapper;
import ru.diploma.project.jd6team5.utils.FullAdsMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Класс описывающий логику получения и обработки информации по сущности объявление
 */
@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final AdsImagesRepository adsImageRepo;
    private final FullAdsMapper fullAdsMapper;
    private final AdsMapper compactMapper;
    private final AdsImagesMapper adsImgMapper;
    @Value("${ads.images.dir.path}")
    private String targetImagesDir;

    public AdsService(AdsRepository adsRepository,
                      AdsImagesRepository adsImageRepo,
                      FullAdsMapper fullAdsMapper,
                      AdsMapper compactMapper,
                      AdsImagesMapper adsImgMapper) {
        this.adsRepository = adsRepository;
        this.adsImageRepo = adsImageRepo;
        this.fullAdsMapper = fullAdsMapper;
        this.compactMapper = compactMapper;
        this.adsImgMapper = adsImgMapper;
    }

    public AdsDto createAds(Long userID, CreateAds createAds, MultipartFile inpPicture) throws IOException {
        Ads newAds = new Ads();
        newAds.setUserID(userID);
        newAds.setDescription(createAds.getDescription());
        newAds.setPrice(createAds.getPrice());
        newAds.setTitle(createAds.getTitle());
        Ads createdAds = adsRepository.save(newAds);
        AdsImages image = new AdsImages();
        image.setAdsId(createdAds.getId());
        AdsImages createdImage = adsImageRepo.save(image);
        Path imagePath = saveIncomeImage(createdImage.getId(), createdAds.getId(), inpPicture);
        if (Files.exists(imagePath)){
            createdImage.setImagePath(imagePath.toFile().getParent());
            createdImage = adsImageRepo.save(createdImage);
            return compactMapper.entityToDto(createdAds);
        } else { throw new ImageFileNotFoundException("Файл с картинкой Объявления не сохранился"); }
    }

    public FullAdsDto findFullAds(Long id) {
        Ads adsFound = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        return fullAdsMapper.entityToDto(adsFound);
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
        List<AdsDto> foundAdsDto = new ArrayList<>(foundAds.size());
        for (Ads a : foundAds) {
            foundAdsDto.add(compactMapper.entityToDto(a));
        }
        ResponseWrapperAds result = new ResponseWrapperAds();
        result.setCount(foundAds.size());
        result.setResults(foundAdsDto);
        return result;
    }

    public ResponseWrapperAds getAllAdsById(long id) {
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

    private Path saveIncomeImage(Long adsID, Long imageID,MultipartFile inpPicture) throws IOException {
        Path imagePath = Path.of(targetImagesDir + "/image_" + adsID + "_" + imageID +
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

    public AdsImagesDto updateAndGetListImages(Long adsID, MultipartFile inpPicture) throws IOException {
        Ads adsFound = adsRepository.findById(adsID).orElseThrow(AdsNotFoundException::new);
        AdsImages imageList = adsImageRepo.getAdsImagesByIdAndImageID(adsFound.getImageListID(), 1L).orElse(null);
        Path imagePath = saveIncomeImage(adsID, 1L, inpPicture);
        if (Files.exists(imagePath)){
            if (imageList == null){ imageList = new AdsImages(); }
            imageList.setId(1L);
            imageList.setImagePath(imagePath.toFile().getPath());
            AdsImagesDto adsImgDto = adsImgMapper.entityToDto(adsImageRepo.save(imageList));
            adsFound.setImageListID(adsImgDto.getId());
            adsRepository.save(adsFound);
            return adsImgDto;
        } else { throw new ImageFileNotFoundException("Не найден файл по указанному пути"); }
    }

    private String getExtensionOfFile(String inpPath){
        if (inpPath.contains(".")){
            return inpPath.substring(inpPath.lastIndexOf("."), inpPath.length());
        } else {
            return "";
        }
    }
}
