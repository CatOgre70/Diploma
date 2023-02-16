package ru.diploma.project.jd6team5.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.diploma.project.jd6team5.dto.*;
import ru.diploma.project.jd6team5.model.Ads;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdsControllerTest {

    @LocalServerPort
    private int locPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdsController adsControl;

    private final ResponseWrapperAds testRespWrapAds = new ResponseWrapperAds();
    private final ResponseWrapperComments testRespWrapCmts = new ResponseWrapperComments();
    private final FullAdsDto testFullAdsDto = new FullAdsDto();
    private final AdsDto testAdsDto = new AdsDto();
    private final CommentDto testCommentDto = new CommentDto();
    private final CreateAds testCreateAds = new CreateAds();
    private final Ads testAds = new Ads(1L, 1L, "Тестовое объявление 1", "Пояснение к тестовому объявлению 1", 12345f);
    private String TEST_URL;

    @Test
    void runControllerTest() {
        assertNotNull(adsControl);
    }

    @BeforeEach
    void setUp() {
        TEST_URL = "http://localhost:" + locPort + "/ads";
        testCreateAds.setDescription("Пояснение к тестовому объявлению 1");
        testCreateAds.setPrice(12345);
        testCreateAds.setTitle("Тестовое объявление 1");

        testCommentDto.setAuthor(1L);
        testCommentDto.setCreatedAt(LocalDateTime.now());
        testCommentDto.setPk(1L);
        testCommentDto.setText("Комментарий тестовый для объявления 1");

        testAdsDto.setAuthor(1);
        testAdsDto.setImage("/path/to/ads/images/image_1.jpg");
        testAdsDto.setPk(1);
        testAdsDto.setPrice(12345);
        testAdsDto.setTitle("Тестовое объявление 1");

        testFullAdsDto.setAuthorFirstName("First Name ads creator");
        testFullAdsDto.setAuthorLastName("Last Name ads creator");
        testFullAdsDto.setDescription(testCreateAds.getDescription());
        testFullAdsDto.setEmail("test@user.com");
        testFullAdsDto.setImage(testAdsDto.getImage());
        testFullAdsDto.setPhone("+79101234567");
        testFullAdsDto.setPk(1L);
        testFullAdsDto.setPrice(testAdsDto.getPrice());
        testFullAdsDto.setTitle(testAdsDto.getTitle());

        testRespWrapCmts.setCount(1L);
        testRespWrapCmts.setResults(List.of(testCommentDto));

        testRespWrapAds.setCount(1L);
        testRespWrapAds.setResults(List.of(testAdsDto));
    }

    @Test
    void getAllAds() {
        assertNotNull(restTemplate.getForObject(TEST_URL, ResponseWrapperAds.class));
    }

    @Test
    void getAllCommentsByAds() {
        assertEquals(
                testRespWrapCmts,
                restTemplate.getForObject(TEST_URL + "/1/comments", ResponseWrapperComments.class)
        );
    }

    @Test
    void getAds() {
        assertEquals(
                testFullAdsDto,
                restTemplate.getForObject(TEST_URL + "/1", FullAdsDto.class)
        );
    }

    @Test
    void addAds() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", getTestFile());
        body.add("properties", testCreateAds);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AdsDto> expectedAdsDto = restTemplate.postForEntity(TEST_URL, requestEntity, AdsDto.class);
        assertEquals(
                expectedAdsDto.getBody(),
                testAdsDto
        );
    }

    @Test
    void addCommentToAds() {
        CommentDto expectedCommentDto = restTemplate.postForObject(TEST_URL + "/1/comments", testCommentDto, CommentDto.class);
        assertEquals(
                expectedCommentDto,
                testCommentDto
        );
    }

    @Test
    void updateAds() {
        testCreateAds.setPrice(54321);
        assertNotEquals(
                testAdsDto,
                restTemplate.patchForObject(TEST_URL + "/1", testCreateAds, AdsDto.class)
        );
    }

    @Test
    void removeAds() {
        restTemplate.delete(TEST_URL + "/1");
        assertNull(
                restTemplate.getForObject(TEST_URL + "/1", FullAdsDto.class)
        );
    }

    @Test
    void getComment() {
        assertEquals(
                testCommentDto,
                restTemplate.getForObject(TEST_URL + "/1/comments/1", CommentDto.class)
        );
    }

    @Test
    void updateComment() {
        testCommentDto.setText("Комментарий изменённый для объявления 1");
        assertNotEquals(
                testCommentDto,
                restTemplate.patchForObject(TEST_URL + "/1/comments/1", testCommentDto, CommentDto.class)
        );
    }

    @Test
    void deleteComment() {
        restTemplate.delete(TEST_URL + "/1/comments/1");
        assertNull(
                restTemplate.getForObject(TEST_URL + "/1/comments/1", CommentDto.class)
        );
    }

    @Test
    void getAdsMe() {
        assertEquals(
                testRespWrapAds,
                restTemplate.getForObject(TEST_URL + "/me", ResponseWrapperAds.class)
        );
    }

    @Test
    void getImage(){
        assertNotNull(restTemplate.getForObject(TEST_URL + "/1/getimage", Arrays.class));
    }
}