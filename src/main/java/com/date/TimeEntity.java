package com.date;

import com.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private DateEntity date;

    @ManyToOne
    @JoinColumn(name = "_user_id")
    private User user;
}
