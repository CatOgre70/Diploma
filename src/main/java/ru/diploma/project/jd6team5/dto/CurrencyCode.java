package ru.diploma.project.jd6team5.dto;

public enum CurrencyCode {
    RUB("рублей"),
    USD("долларов"),
    EUR("евро");

    private final String description;

    CurrencyCode(String description) {
        this.description = description;
    }
}
