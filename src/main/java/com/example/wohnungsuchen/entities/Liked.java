package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "liked")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Offers offer;

    @ManyToOne()
    @JoinColumn(name = "searcher_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Searchers searcher;
    public Liked() {
    }
}
