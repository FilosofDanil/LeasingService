package com.example.wohnungsuchen.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Set;

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
    @NotNull
    private Credentials credentials;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "leaseholders", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Offers> offers;

    @Column(name = "firma_name")
    private String firma_name;
    public Leaseholders() {
    }
}
