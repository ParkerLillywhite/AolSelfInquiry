package com.date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {

    List<TimeEntity> findByDate(DateEntity dateEntity);

    @Query("DELETE FROM TimeEntity t WHERE t.date = :dateEntity")
    void deleteByEntity(@Param("dateEntity") DateEntity dateEntity);
}
