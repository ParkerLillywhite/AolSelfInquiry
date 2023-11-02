package com.date;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {

    List<TimeEntity> findByDate(DateEntity dateEntity);
}
