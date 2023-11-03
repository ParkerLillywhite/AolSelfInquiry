package com.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.firstname, u.lastname, u.email, u.role FROM User u WHERE u.email = :email")
    Optional<Object[]> findUserDataByEmail(@Param("email") String email);
}
