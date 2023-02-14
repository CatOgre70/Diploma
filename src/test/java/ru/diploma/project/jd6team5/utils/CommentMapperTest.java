package ru.diploma.project.jd6team5.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diploma.project.jd6team5.dto.AdsDto;
import ru.diploma.project.jd6team5.dto.CommentDto;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.Comment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentMapperTest {
    @Autowired
    private CommentMapper mapper;
    private Comment comment;
    private CommentDto dto;
    @BeforeEach
    void init() {
        comment = new Comment(1L, 11L, 1L, "text", null);
        dto = new CommentDto(11L, null, 1L, "text");
    }
    @Test
    void entityToDto() {
        CommentDto testDto = mapper.entityToDto(comment);
        Assertions.assertEquals(testDto, dto);
    }

    @Test
    void dtoToEntity() {
        Comment testComment = mapper.dtoToEntity(dto);
        comment.setAdsID(null);
        Assertions.assertEquals(testComment, comment);
    }
}