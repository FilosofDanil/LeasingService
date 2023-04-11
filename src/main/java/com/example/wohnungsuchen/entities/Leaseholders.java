package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "leaseholders")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Leaseholders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "id", nullable = false, unique = true)
    private Credits credits;

    @Column(name = "firma_name")
    private String firma_name;
    public Leaseholders() {
    }
}
