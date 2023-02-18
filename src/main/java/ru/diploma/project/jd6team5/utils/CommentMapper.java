package ru.diploma.project.jd6team5.utils;

import org.springframework.stereotype.Component;
import ru.diploma.project.jd6team5.dto.CommentDto;
import ru.diploma.project.jd6team5.model.Comment;

@Component
public class CommentMapper {

    public CommentDto entityToDto(Comment comment) {
        return new CommentDto(comment.getUserID(),
                comment.getCreateDate(),
                comment.getId(),
                comment.getCommentText());
    }

    public Comment dtoToEntity(CommentDto dto) {
        return new Comment(dto.getPk(),
                dto.getAuthor(),
                null,
                dto.getText(),
                dto.getCreatedAt());
    }
}
