package ru.diploma.project.jd6team5.service;

import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.dto.CommentDto;
import ru.diploma.project.jd6team5.dto.ResponseWrapperComments;
import ru.diploma.project.jd6team5.exception.CommentNotFoundException;
import ru.diploma.project.jd6team5.model.Comment;
import ru.diploma.project.jd6team5.repository.CommentRepository;
import ru.diploma.project.jd6team5.utils.CommentMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывающий логику получения и обработки информации по сущности коммент
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, CommentMapper mapper,
                          UserService userService) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public CommentDto addComment(Long adsID, CommentDto dto, String authUserName) {
        Comment newComment = mapper.dtoToEntity(dto);
        newComment.setAdsID(adsID);
        newComment.setUserID(userService.getUserIdByName(authUserName));
        Comment result = commentRepository.save(newComment);
        return mapper.entityToDto(result);
    }

    public CommentDto findById(Long adsID, Long id) {
        return mapper.entityToDto(commentRepository.findById(id).orElseThrow(CommentNotFoundException::new));
    }

    public CommentDto findByIdAndAdsId(Long adsID, Long id) {
        return mapper.entityToDto(commentRepository.findCommentByIdAndAdsID(id, adsID).orElseThrow(CommentNotFoundException::new));
    }

    public CommentDto updateComment(Long adsID, Long commentID, CommentDto dto) {
        Comment foundComment = commentRepository.findCommentByIdAndAdsID(commentID, adsID).orElseThrow(CommentNotFoundException::new);
        foundComment.setCreateDate(dto.getCreatedAt());
        foundComment.setCommentText(dto.getText());
        foundComment.setUserID(dto.getAuthor());
        Comment result = commentRepository.save(foundComment);
        return mapper.entityToDto(result);
    }

    public void deleteComment(Long adsId, Long commentId) {
        Comment comment = commentRepository.findCommentByIdAndAdsID(commentId, adsId).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }

    public ResponseWrapperComments getAllCommentsByAdsId(long id) {
        List<Comment> foundComments = commentRepository.findAllByAdsID(id);
        ResponseWrapperComments response = new ResponseWrapperComments();
        List<CommentDto> dtoList = new ArrayList<>();
        for(Comment c : foundComments) {
            dtoList.add(mapper.entityToDto(c));
        }
        response.setCount(foundComments.size());
        response.setResults(dtoList);
        return response;
    }
}
