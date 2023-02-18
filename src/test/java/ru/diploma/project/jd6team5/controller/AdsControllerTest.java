package ru.diploma.project.jd6team5.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.CommentDto;
import ru.diploma.project.jd6team5.dto.CreateAds;
import ru.diploma.project.jd6team5.dto.FullAdsDto;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.Comment;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.repository.AdsRepository;
import ru.diploma.project.jd6team5.repository.CommentRepository;
import ru.diploma.project.jd6team5.repository.UserRepository;
import ru.diploma.project.jd6team5.service.AdsService;
import ru.diploma.project.jd6team5.service.AuthService;
import ru.diploma.project.jd6team5.service.CommentService;
import ru.diploma.project.jd6team5.service.UserService;
import ru.diploma.project.jd6team5.utils.AdsMapper;
import ru.diploma.project.jd6team5.utils.CommentMapper;
import ru.diploma.project.jd6team5.utils.FullAdsMapper;
import ru.diploma.project.jd6team5.utils.UserMapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdsController.class)
class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdsRepository adsRepo;
    @MockBean
    private CommentRepository commentRepo;
    @MockBean
    private UserRepository userRepo;

    @MockBean
    private AdsMapper adsMapper;
    @MockBean
    private FullAdsMapper fullAdsMapper;
    @MockBean
    private CommentMapper commentMapper;

    @SpyBean
    private AdsService adsService;
    @SpyBean
    private CommentService comService;
    @SpyBean
    private UserService userService;
    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private AuthService checkAuth;

    @InjectMocks
    private AdsController adsController;

    private final FullAdsDto testFullAdsDto = new FullAdsDto();
    private final AdsDto testAdsDto = new AdsDto();
    private final CommentDto testCommentDto = new CommentDto();
    private final CreateAds testCreateAds = new CreateAds();
    private final Ads testAds = new Ads(1L, 1L, "Тестовое объявление 1", "Пояснение к тестовому объявлению 1", 12345f);
    private final User testUser = new User();
    private final Comment testComment = new Comment();

    @Test
    void runControllerTest() {
        assertNotNull(adsController);
    }

    @BeforeEach
    void setUp() {

        testCreateAds.setDescription("Пояснение к тестовому объявлению 1");
        testCreateAds.setPrice(12345);
        testCreateAds.setTitle("Тестовое объявление 1");

        testCommentDto.setAuthor(1L);
        testCommentDto.setCreatedAt(LocalDateTime.now());
        testCommentDto.setPk(1L);
        testCommentDto.setText("Комментарий тестовый для объявления 1");

        testComment.setId(1L);
        testComment.setUserID(1L);
        testComment.setAdsID(1L);
        testComment.setCreateDate(LocalDateTime.now());
        testComment.setCommentText("Новый комментарий 1 к Объявлению 1");

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

        testUser.setUsername("user@gmail.com");
        testUser.setEmail("user@gmail.com");
        testUser.setPassword("12345");
        testUser.setUserID(1L);
    }

    @WithMockUser(value = "user@gmail.com")
    @Test
    void getAllAds() throws Exception {
        when(adsRepo.findAll()).thenReturn(List.of(testAds));
        when(adsMapper.entityToDto(any(Ads.class))).thenReturn(testAdsDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void getAllCommentsByAds() throws Exception {
        when(commentRepo.findAllByAdsID(any(Long.class))).thenReturn(List.of(testComment));
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(testCommentDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1/comments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void getAds() throws Exception {
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        when(fullAdsMapper.entityToDto(any(Ads.class))).thenReturn(testFullAdsDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@user.com"))
                .andExpect(jsonPath("$.phone").value("+79101234567"))
                .andExpect(jsonPath("$.title").value("Тестовое объявление 1"));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void addAds() throws Exception {
        byte[] test_file = "Image Content".getBytes(StandardCharsets.UTF_8);
        when(adsRepo.save(any(Ads.class))).thenReturn(testAds);
        when(adsMapper.entityToDto(any(Ads.class))).thenReturn(testAdsDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads") //send
                        .content(testCreateAds.toString())
                        .content(test_file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest()); //receive
//                .andExpect(jsonPath("$.author").value(1))
//                .andExpect(jsonPath("$.image").value("/path/to/ads/images/image_1.jpg"))
//                .andExpect(jsonPath("$.pk").value(1))
//                .andExpect(jsonPath("$.price").value(12345))
//                .andExpect(jsonPath("$.title").value("Тестовое объявление 1"));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void addCommentToAds() throws Exception {
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        when(fullAdsMapper.entityToDto(any(Ads.class))).thenReturn(testFullAdsDto);
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(testCommentDto);
        when(commentRepo.save(any(Comment.class))).thenReturn(testComment);
        when(userRepo.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ads/1/comments") //send
                        .content(testCommentDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest()); //receive
//                .andExpect(jsonPath("$.author").value(1L))
//                .andExpect(jsonPath("$.pk").value(1L))
//                .andExpect(jsonPath("$.text").value("Комментарий тестовый для объявления 1"));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void updateAds() throws Exception {
        testCreateAds.setPrice(54321);
        testAdsDto.setPrice(54321);
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        when(userRepo.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        when(adsRepo.save(any(Ads.class))).thenReturn(testAds);
        when(adsMapper.entityToDto(any(Ads.class))).thenReturn(testAdsDto);
        mockMvc.perform(MockMvcRequestBuilders
                    .patch("/ads/1") //send
                    .content(testCreateAds.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .with(csrf())
                )
                .andExpect(status().isBadRequest()); //receive
//                .andExpect(jsonPath("$.author").value(1))
//                .andExpect(jsonPath("$.image").value("/path/to/ads/images/image_1.jpg"))
//                .andExpect(jsonPath("$.pk").value(1))
//                .andExpect(jsonPath("$.price").value(54321))
//                .andExpect(jsonPath("$.title").value("Тестовое объявление 1"));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void removeAds() throws Exception {
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        when(userRepo.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        when(checkAuth.dasActionPermit(any(Long.class), any(Long.class), any(UserRole.class))).thenReturn(true);
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1") //send
                        .accept(MediaType.TEXT_HTML_VALUE)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void getComment() throws Exception {
        when(commentRepo.findCommentByIdAndAdsID(any(Long.class), any(Long.class))).thenReturn(Optional.of(testComment));
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(testCommentDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1/comments/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(1L))
                .andExpect(jsonPath("$.pk").value(1L))
                .andExpect(jsonPath("$.text").value("Комментарий тестовый для объявления 1"));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void updateComment() throws Exception {
        testCommentDto.setText("Комментарий изменённый для объявления 1");
        when(commentRepo.findCommentByIdAndAdsID(any(Long.class), any(Long.class))).thenReturn(Optional.of(testComment));
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(testCommentDto);
        when(userRepo.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        when(checkAuth.dasActionPermit(any(Long.class), any(Long.class), any(UserRole.class))).thenReturn(true);
        when(commentRepo.findCommentByIdAndAdsID(any(Long.class), any(Long.class))).thenReturn(Optional.of(testComment));
        when(commentRepo.save(any(Comment.class))).thenReturn(testComment);
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(testCommentDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1/comments/1")
                        .content(testCommentDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.author").value(1L))
//                .andExpect(jsonPath("$.pk").value(1L))
//                .andExpect(jsonPath("$.text").value("Комментарий изменённый для объявления 1"));
    }

    @WithMockUser(username = "user@gmail.com")
    @Test
    void deleteComment() throws Exception {
        when(commentRepo.findCommentByIdAndAdsID(any(Long.class), any(Long.class))).thenReturn(Optional.of(testComment));
        when(commentMapper.entityToDto(any(Comment.class))).thenReturn(testCommentDto);
        when(userRepo.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        when(checkAuth.dasActionPermit(any(Long.class), any(Long.class), any(UserRole.class))).thenReturn(true);
        when(commentRepo.findCommentByIdAndAdsID(any(Long.class), any(Long.class))).thenReturn(Optional.of(testComment));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1/comments/1")
                        .accept(MediaType.TEXT_HTML_VALUE)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "user@gmail.com")
    @Test
    void getAdsMe() throws Exception {
        when(userRepo.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        when(adsRepo.findAllByUserID(any(Long.class))).thenReturn(List.of(testAds));
        when(adsMapper.entityToDto(any(Ads.class))).thenReturn(testAdsDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1L));
    }

    @WithMockUser(value = "user@gmail.com")
    @Test
    void getImage() throws Exception {
        when(adsRepo.findById(any(Long.class))).thenReturn(Optional.of(testAds));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1/getimage")
                        .accept(MediaType.IMAGE_PNG_VALUE))
                .andExpect(status().isOk());
    }
}