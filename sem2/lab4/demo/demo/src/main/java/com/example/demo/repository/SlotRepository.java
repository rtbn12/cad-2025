package com.example.demo.repository;

import com.example.demo.entity.Slot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Query(value = """
        SELECT s.*
        FROM public.slots s
        WHERE s.doctor_id = :doctorId
          AND s.slot_start >= :dayStart
          AND s.slot_start < :nextDayStart
        ORDER BY s.slot_start
        """, nativeQuery = true)
    List<Slot> findSlotsForDay(
            @Param("doctorId") Long doctorId,
            @Param("dayStart") LocalDateTime dayStart,
            @Param("nextDayStart") LocalDateTime nextDayStart
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Slot s WHERE s.id = :id")
    Optional<Slot> findByIdForUpdate(@Param("id") Long id);


    @Query(value = """
        SELECT DISTINCT CAST(s.slot_start AS date) AS available_date
        FROM public.slots s
        WHERE s.doctor_id = :doctorId
          AND s.slot_end > :now
        ORDER BY available_date
        """, nativeQuery = true)
    List<LocalDate> findDatesForRegistrar(
            @Param("doctorId") Long doctorId,
            @Param("now") LocalDateTime now
    );

}