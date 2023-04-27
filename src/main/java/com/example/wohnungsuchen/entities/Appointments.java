package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull
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
    @NotNull
    private Leaseholders leaseholder;

    @Column(name = "meeting_date", nullable = false)
    @NotNull
    private Date meeting_date;
    @Column(name = "meeting_time", nullable = false)
    @NotNull
    @Pattern(regexp = "^[0-1][0-9]:[0-5][0-9]|[1-2][0-3]:[0-5][0-9]$")
    private Time meeting_time;
    @Column(name = "description", nullable = true)
    private String description;

    public Appointments() {
    }
}
