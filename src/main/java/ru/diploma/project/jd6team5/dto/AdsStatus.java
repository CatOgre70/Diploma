package ru.diploma.project.jd6team5.dto;

public enum AdsStatus {
    ACTIVE("Актуально"),
    ARCHIVED("Архив"),
    CLOSED("Закрыто"),
    DELETED("Удалено");

    private final String description;

    AdsStatus(String description) {
        this.description = description;
    }
}
