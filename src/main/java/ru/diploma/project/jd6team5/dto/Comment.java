package ru.diploma.project.jd6team5.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "ads_id")
    private Long adsId;
    private String text;
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
