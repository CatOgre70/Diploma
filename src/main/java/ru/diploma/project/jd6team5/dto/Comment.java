package ru.diploma.project.jd6team5.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID = 1L;
    @Column(name = "ads_id")
    private Long adsID = 1L;
    private String comment = "Просто мой комментарий к Объявлению";
    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    public Comment(Long userID, Long adsID, String comment, LocalDateTime createDate) {
        this.userID = userID;
        this.adsID = adsID;
        this.comment = comment;
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(getUserID(), comment1.getUserID()) && Objects.equals(getAdsID(), comment1.getAdsID()) && Objects.equals(getComment(), comment1.getComment()) && Objects.equals(this.getCreateDate(), comment1.getCreateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getAdsID(), this.getCreateDate());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "userID=" + userID +
                ", adsID=" + adsID +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
