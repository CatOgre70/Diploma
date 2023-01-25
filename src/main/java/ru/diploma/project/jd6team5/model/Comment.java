package ru.diploma.project.jd6team5.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID;
    @Column(name = "ads_id")
    private Long adsID;
    @Column(name = "comment")
    private String commentText;
    @Column(name = "create_date")
    private LocalDateTime createDate;


}
