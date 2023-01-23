package ru.diploma.project.jd6team5.dto;

import lombok.*;
import ru.diploma.project.jd6team5.model.Comment;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ResponseWrapperComments {
    private long count;
    private List<Comment> results;
}
