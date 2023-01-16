package ru.diploma.project.jd6team5.model;

import ru.diploma.project.jd6team5.dto.AdsStatus;
import ru.diploma.project.jd6team5.dto.CurrencyCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class FullAds {
    private String authorFirstName = "Иван";
    private String authorLastName = "Петрович";
    private String description = "Набор ручного инструмента предназначенный для ремонта автотехники";
    private String email = "user@mail.com";
    private Long imageListID = 2L;
    private String phone = "84951234567";
    private Long id;
    private Float price = 5000.67f;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency = CurrencyCode.RUB;
    private String label = "Набор авто-инструментов фирмы \"Автотехник\"";
    @Enumerated(EnumType.STRING)
    private AdsStatus status = AdsStatus.ACTIVE;

    public FullAds(String authorFirstName,
                   String authorLastName,
                   String description,
                   String email,
                   Long imageListID,
                   String phone,
                   Long id,
                   Float price,
                   CurrencyCode currency,
                   String label,
                   AdsStatus status) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.description = description;
        this.email = email;
        this.imageListID = imageListID;
        this.phone = phone;
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.label = label;
        this.status = status;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getImageListID() {
        return imageListID;
    }

    public void setImageListID(Long imageListID) {
        this.imageListID = imageListID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AdsStatus getStatus() {
        return status;
    }

    public void setStatus(AdsStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullAds)) return false;
        FullAds fullAds = (FullAds) o;
        return Objects.equals(getAuthorFirstName(), fullAds.getAuthorFirstName()) && Objects.equals(getAuthorLastName(), fullAds.getAuthorLastName()) && Objects.equals(getDescription(), fullAds.getDescription()) && Objects.equals(getEmail(), fullAds.getEmail()) && Objects.equals(getImageListID(), fullAds.getImageListID()) && Objects.equals(getPhone(), fullAds.getPhone()) && Objects.equals(getId(), fullAds.getId()) && Objects.equals(getPrice(), fullAds.getPrice()) && getCurrency() == fullAds.getCurrency() && Objects.equals(getLabel(), fullAds.getLabel()) && getStatus() == fullAds.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorFirstName(), getAuthorLastName(), getDescription());
    }

    @Override
    public String toString() {
        return "FullAds{" +
                "authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", imageListID=" + imageListID +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", currency=" + currency +
                ", label='" + label + '\'' +
                ", status=" + status +
                '}';
    }
}
