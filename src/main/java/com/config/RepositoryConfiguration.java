package com.config;

import com.date.DateRepository;
import com.date.TimeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration
public class RepositoryConfiguration {
    //workaround for Spring not being able to find repository.
    @Bean
    public DateRepository dateRepository(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager).getRepository(DateRepository.class);
    }

    @Bean
    public TimeRepository timeRepository(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager).getRepository(TimeRepository.class);
    }
}
