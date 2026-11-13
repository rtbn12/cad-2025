package com.example.demo.repository;

import com.example.demo.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query(value = """
        SELECT DISTINCT CAST(s.slot_start AS date) AS available_date
        FROM public.slots s
        WHERE s.doctor_id = :doctorId
          AND s.status = 'FREE'
          AND s.slot_start > :now
        ORDER BY available_date
        """, nativeQuery = true)
    List<LocalDate> findAvailableDates(
            @Param("doctorId") Long doctorId,
            @Param("now") LocalDateTime now
    );

}
