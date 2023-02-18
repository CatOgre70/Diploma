package ru.diploma.project.jd6team5.constants;

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
