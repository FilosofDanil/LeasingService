package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "assignments")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Assignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "searcher_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Searchers searcher;
    @ManyToOne()
    @JoinColumn(name = "appointment_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Appointments appointment;
    @Column(name = "notified", nullable = true)
    private Boolean notified;

    public Assignments() {

    }
}