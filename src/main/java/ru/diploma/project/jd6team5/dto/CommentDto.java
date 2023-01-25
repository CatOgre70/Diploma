package ru.diploma.project.jd6team5.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private Long userID;
    private Long adsID;
    private String commentText;
    private LocalDateTime createDate;

}
