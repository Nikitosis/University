package com.examproject.variant40.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "hated_students_enhancement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HatedStudentsEnhancement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "hated_student_id")
    private Student hatedStudent;

    private BigDecimal coefficient;
}
