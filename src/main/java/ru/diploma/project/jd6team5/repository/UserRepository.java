package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diploma.project.jd6team5.dto.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /** Получение Пользователя по его ИД номеру */
    Optional<User> findUserByID(Long userID);

}
