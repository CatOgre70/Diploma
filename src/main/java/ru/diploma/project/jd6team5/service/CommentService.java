package ru.diploma.project.jd6team5.service;

import org.springframework.stereotype.Service;
import ru.diploma.project.jd6team5.dto.CommentDto;
import ru.diploma.project.jd6team5.dto.ResponseWrapperAds;
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

    public CommentService(CommentRepository commentRepository, CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    public CommentDto addComment(CommentDto dto) {
        Comment result = commentRepository.save(mapper.dtoToEntity(dto));
        return mapper.entityToDto(result);
    }

    public CommentDto findById(Long adsID, Long id) {
        return mapper.entityToDto(commentRepository.findById(id).orElseThrow(CommentNotFoundException::new));
    }

    public CommentDto findByIdAndAdsId(Long adsID, Long id) {
        return mapper.entityToDto(commentRepository.findCommentByIdAndAdsID(id, adsID).orElseThrow(CommentNotFoundException::new));
    }

    public CommentDto updateComment(CommentDto dto) {
        Comment result =  commentRepository.save(mapper.dtoToEntity(dto));
        return mapper.entityToDto(result);
    }
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
    }

    public ResponseWrapperComments getAllCommentsByAdsId(long id) {
        List<Comment> foundComments = commentRepository.findAllByAdsID(id);
        ResponseWrapperComments response = new ResponseWrapperComments();
        response.setCount(foundComments.size());
        response.setResults(foundComments);
        return response;
    }
}
