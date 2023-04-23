package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
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
    private Searchers searcher;
    @ManyToOne()
    @JoinColumn(name = "appointment_id", nullable = false, referencedColumnName = "id")
    private Appointments appointment;
    @Column(name = "notified", nullable = true)
    private Boolean notified;

    public Assignments() {

    }
}