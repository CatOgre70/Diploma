package ru.diploma.project.jd6team5.service;

import liquibase.pro.packaged.R;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.CommentDto;
import ru.diploma.project.jd6team5.dto.ResponseWrapperComments;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.exception.CommentNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.Comment;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.repository.CommentRepository;
import ru.diploma.project.jd6team5.utils.CommentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
class CommentServiceTest {
    @Mock
    private CommentRepository repository;
    @Mock
    private CommentMapper mapper;
    @Mock
    private UserService userService;
    @Spy
    @InjectMocks
    private CommentService service;
    private Comment comment;
    private CommentDto dto;
    private User user;
    private Long adsId = 1L;
    private String userName = "name";
    private Long userId = 11L;

    @BeforeEach
    void init() {
        comment = new Comment(1L, 11L, 1L, "text", null);
        dto = new CommentDto(11L, null, 1L, "text");

    }

    @Test
    void addComment() {
        when(mapper.dtoToEntity(dto)).thenReturn(comment);
        when(userService.getUserIdByName(userName)).thenReturn(userId);
        service.addComment(adsId, dto, userName);
        verify(repository).save(any(Comment.class));
    }

    @Test
    void findById() {
        when(repository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(mapper.entityToDto(comment)).thenReturn(dto);
        Assertions.assertEquals(dto, service.findById(1L, comment.getId()));
    }

    @Test
    void findByIdAndAdsId() {
        when(repository.findCommentByIdAndAdsID(comment.getId(), 1L)).thenReturn(Optional.of(comment));
        when(mapper.entityToDto(comment)).thenReturn(dto);
        Assertions.assertEquals(dto, service.findByIdAndAdsId(1L, comment.getId()));
    }

    @Test
    void updateComment() {
        comment.setCommentText("commentText");
        when(repository.findCommentByIdAndAdsID(1L, comment.getId())).thenReturn(Optional.of(comment));
        when(mapper.entityToDto(comment)).thenReturn(dto);
        when(repository.save(any(Comment.class))).thenReturn(comment);
        Assertions.assertEquals(dto, service.updateComment(1L, comment.getId(), dto));
        when(repository.findById(comment.getId())).thenReturn(null);
        Assertions.assertThrows(CommentNotFoundException.class, () -> {throw new CommentNotFoundException();});
    }

    @Test
    void deleteComment() {
        when(repository.findCommentByIdAndAdsID(1L, comment.getId())).thenReturn(Optional.of(comment));
        service.deleteComment(1L, comment.getId());
        verify(repository).delete(any());
        when(repository.findCommentByIdAndAdsID(1L, comment.getId())).thenReturn(null);
        Assertions.assertThrows(CommentNotFoundException.class, () -> {throw new CommentNotFoundException();});
    }

    @Test
    void getAllCommentsByAdsId() {
        ResponseWrapperComments wrapper = new ResponseWrapperComments();
        List<CommentDto> dtoList = new ArrayList<>();
        dtoList.add(dto);
        wrapper.setResults(dtoList);
        wrapper.setCount(dtoList.size());
        when(mapper.entityToDto(comment)).thenReturn(dto);
        when(repository.findAllByAdsID(adsId)).thenReturn(new ArrayList<>(List.of(comment)));
        Assertions.assertEquals(wrapper, service.getAllCommentsByAdsId(adsId));
    }
}