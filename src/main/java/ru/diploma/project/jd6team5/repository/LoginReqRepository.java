package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diploma.project.jd6team5.dto.LoginReq;

@Repository
public interface LoginReqRepository extends JpaRepository<LoginReq, Long> {
}
