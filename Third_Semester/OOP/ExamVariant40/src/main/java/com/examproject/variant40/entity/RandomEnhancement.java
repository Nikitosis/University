package com.examproject.variant40.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "random_enhancement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RandomEnhancement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "min_coefficient")
    private BigDecimal minCoefficient;

    @Column(name = "max_coefficient")
    private BigDecimal maxCoefficient;
}
