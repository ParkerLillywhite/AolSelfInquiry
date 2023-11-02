package com.date;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DateRepository extends JpaRepository<DateEntity, Long> {
    Optional<DateEntity> findByDate(Date date);

    List<DateEntity> findByDisabledTrue();
}
