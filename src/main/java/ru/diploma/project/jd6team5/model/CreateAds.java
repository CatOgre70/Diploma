package ru.diploma.project.jd6team5.model;

import ru.diploma.project.jd6team5.dto.AdsStatus;
import ru.diploma.project.jd6team5.dto.CurrencyCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

public class CreateAds {
    private String label = "Набор авто-инструментов фирмы \"Автотехник\"";
    private String description = "Набор ручного инструмента предназначенный для ремонта автотехники";
    private Float price = 5000.67f;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency = CurrencyCode.RUB;
    @Enumerated(EnumType.STRING)
    private AdsStatus status = AdsStatus.ACTIVE;

    public CreateAds(String label, String description, Float price, CurrencyCode currency, AdsStatus status) {
        this.label = label;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public AdsStatus getStatus() {
        return status;
    }

    public void setStatus(AdsStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAds)) return false;
        CreateAds createAds = (CreateAds) o;
        return Objects.equals(getLabel(), createAds.getLabel()) && Objects.equals(getDescription(), createAds.getDescription()) && Objects.equals(getPrice(), createAds.getPrice()) && getCurrency() == createAds.getCurrency() && getStatus() == createAds.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getPrice(), getStatus());
    }
}
