package ru.diploma.project.jd6team5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diploma.project.jd6team5.dto.RegisterReq;

import java.util.Optional;

@Repository
public interface RegisterReqRepository extends JpaRepository<RegisterReq, Long> {
    Optional<RegisterReq> getRegisterReqByUserID(Long userID);
}
