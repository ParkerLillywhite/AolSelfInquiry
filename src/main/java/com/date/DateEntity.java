package com.date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "date_entities")
public class DateEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    private Boolean disabled;

    @OneToMany(mappedBy = "date", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeEntity> times;


}
