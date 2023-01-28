package ru.diploma.project.jd6team5.dto;

import lombok.*;
import ru.diploma.project.jd6team5.model.Comment;

import java.util.List;

@NoArgsConstructor
@Data
public class ResponseWrapperComments {
    private long count;
    private List<Comment> results;
}
