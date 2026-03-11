package ru.bsuedu.cad.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bsuedu.cad.lab.entity.Order_Details;

@Repository
public interface OrderDetailsRepository extends JpaRepository<Order_Details, Long> {
}
