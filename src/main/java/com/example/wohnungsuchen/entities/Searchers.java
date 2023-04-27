package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @NotNull
    private Credentials credentials;

    @Column(name = "city")
    @NotBlank
    @Length(min = 2)
    private String city;

    @Column(name = "notifications", nullable = false)
    private Boolean notifications;

    public Searchers() {
    }
}
