package ru.diploma.project.jd6team5.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private Long author;
    private LocalDateTime createdAt;
    private Long pk;
    private String text;
}
