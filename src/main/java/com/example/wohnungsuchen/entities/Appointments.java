package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

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

    @ManyToOne()
    @JoinColumn(name = "searcher_id", referencedColumnName = "id", nullable = false)
    private Searchers searcher;

    @ManyToOne()
    @JoinColumn(name = "leaseholder_id", referencedColumnName = "id", nullable = false)
    private Leaseholders leaseholder;

    @Column(name = "meeting_date",nullable = false)
    private Date meeting_date;
    @Column(name = "meeting_time",nullable = false)
    private Time meeting_time;
    @Column(name="description",nullable = true)
    private String description;
    public Appointments() {
    }
}
