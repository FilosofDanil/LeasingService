package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "searchers")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Searchers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "id", nullable = false, unique = true)
    private Credentials credentials;

    @Column(name = "city")
    private String city;
    public Searchers() {
    }
}
