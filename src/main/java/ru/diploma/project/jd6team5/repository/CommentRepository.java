package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diploma.project.jd6team5.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAdsID(long id);

    Optional<Comment> findCommentByIdAndAdsID(Long comID, Long adsID);
}
