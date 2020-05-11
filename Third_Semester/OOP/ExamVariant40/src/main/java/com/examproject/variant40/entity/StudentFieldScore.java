package com.examproject.variant40.entity;

import com.sun.imageio.plugins.common.LZWCompressor;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student_field_score")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentFieldScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "field_id")
    private SportsField sportsField;

    @Min(0)
    @Max(100)
    @NotNull
    private Integer score;
}
