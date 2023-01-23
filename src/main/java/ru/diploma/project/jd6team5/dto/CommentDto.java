package ru.diploma.project.jd6team5.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentDto {
    private Long id;
    private Long userID;
    private Long adsID;
    private String commentText;
    private LocalDateTime createDate;

}
