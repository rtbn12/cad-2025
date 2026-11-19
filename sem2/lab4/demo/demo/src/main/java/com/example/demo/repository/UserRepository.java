package com.example.demo.repository;

import com.example.demo.entity.User;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByLogin (String login);

     boolean existsByLogin(String login);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLoginForUpdate(@Param("login") String login);
}
