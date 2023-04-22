package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "appointments")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
    private Offers offer;

    @ManyToMany
    @JoinTable(
            name = "assignments",
            joinColumns = {@JoinColumn(name = "appointment_id")},
            inverseJoinColumns = {@JoinColumn(name = "searcher_id")}
    )
    private Set<Searchers> searchers = new HashSet<>();


    @ManyToOne()
    @JoinColumn(name = "leaseholder_id", referencedColumnName = "id", nullable = false)
    private Leaseholders leaseholder;

    @Column(name = "meeting_date", nullable = false)
    private Date meeting_date;
    @Column(name = "meeting_time", nullable = false)
    private Time meeting_time;
    @Column(name = "description", nullable = true)
    private String description;

    public Appointments() {
    }
}
