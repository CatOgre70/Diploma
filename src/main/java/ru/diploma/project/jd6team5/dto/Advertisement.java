package ru.diploma.project.jd6team5.dto;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "ads")
@Getter
@Setter
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userID = 1L;
    private String label = "Набор авто-инструментов фирмы \"Автотехник\"";
    private String description = "Набор ручного инструмента предназначенный для ремонта автотехники";
    private Float price = 5000.67f;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency = CurrencyCode.RUB;
    @Enumerated(EnumType.STRING)
    private AdsStatus status = AdsStatus.ACTIVE;
    @Column(name = "image_list_id")
    private Long imageListID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserID(), that.getUserID()) && Objects.equals(getLabel(), that.getLabel()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && getCurrency() == that.getCurrency() && getStatus() == that.getStatus() && Objects.equals(getImageListID(), that.getImageListID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserID(), getLabel(), getStatus(), getImageListID());
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", userID=" + userID +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", status=" + status +
                ", imageListID=" + imageListID +
                '}';
    }
}
