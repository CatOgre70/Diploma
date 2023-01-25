package ru.diploma.project.jd6team5.constants;

public enum UserRole {
    USER("Пользователь"),
    ADMIN("Администратор");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
