package com.examproject.variant40.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sports_field")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SportsField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
}
