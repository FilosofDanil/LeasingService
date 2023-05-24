package com.example.wohnungsuchen.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

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
    @Length(min = 2)
    private String city;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "searcher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Liked> likes;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "searcher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Assignments> assignments;

    @Column(name = "notifications", nullable = false)
    private Boolean notifications;

    public Searchers() {
    }
}
