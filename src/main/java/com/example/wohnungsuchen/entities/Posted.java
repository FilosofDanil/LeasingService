package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posted")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Posted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "leaseholder_id", referencedColumnName = "id", nullable = false)
    private Leaseholders leaseholder;
    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
    private Offers offer;

    public Posted() {

    }
}
