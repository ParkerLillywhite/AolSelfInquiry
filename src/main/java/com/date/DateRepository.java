package com.date;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@AutoConfiguration
public interface DateRepository extends JpaRepository<DateEntity, Long> {
    Optional<DateEntity> findByDate(Date date);

    List<DateEntity> findByDisabledTrue();
}
