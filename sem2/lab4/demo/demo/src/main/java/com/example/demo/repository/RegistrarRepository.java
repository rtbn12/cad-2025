package com.example.demo.repository;
import com.example.demo.entity.Registrar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RegistrarRepository extends JpaRepository<Registrar, Long> {

    Optional<Registrar> findByUser_Id(Long userId);
}
