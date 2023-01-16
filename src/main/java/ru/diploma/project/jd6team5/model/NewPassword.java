package ru.diploma.project.jd6team5.model;

import java.util.Objects;

public class NewPassword {
    private String currentPassword;
    private String newPassword;

    public NewPassword(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewPassword)) return false;
        NewPassword that = (NewPassword) o;
        return Objects.equals(getCurrentPassword(), that.getCurrentPassword()) && Objects.equals(getNewPassword(), that.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentPassword(), getNewPassword());
    }

    @Override
    public String toString() {
        return "\"NewPassword\": {" +
                "\"currentPassword\": \"" + currentPassword + "\"" +
                ", \"newPassword\": \"" + newPassword + "\"" +
                "}";
    }
}
