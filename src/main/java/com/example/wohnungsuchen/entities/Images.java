package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="link",nullable = false)
    @NotBlank
    private String link;
    public Images() {
    }
}
