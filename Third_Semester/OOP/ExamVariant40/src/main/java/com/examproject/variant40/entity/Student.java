package com.examproject.variant40.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student", fetch = FetchType.LAZY)
    private Set<StudentFieldScore> fieldScores;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Competition> competitions;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student", fetch = FetchType.LAZY)
    private Set<CompetitionResults> competitionResults;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student", fetch = FetchType.LAZY)
    private Set<HatedStudentsEnhancement> hatedStudentsEnhancements;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student", fetch = FetchType.LAZY)
    private RandomEnhancement randomEnhancement;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student", fetch = FetchType.LAZY)
    private PreviousCompetitionEnhancement previousCompetitionEnhancement;
}
