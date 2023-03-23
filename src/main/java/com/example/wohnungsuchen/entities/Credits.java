package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "credits")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Credits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="profile_name", nullable = false)
    private String profile_name;
    @Column(name = "surname",nullable = false)
    private String surname;
    @Column(name="phone", nullable = false)
    private String phone;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name = "date_of_birth", nullable = false)
    private Date birthDate;
    @Column(name ="profile_password", nullable = false)
    private String password;
    @Column(name="verified", nullable = true)
    private Boolean verified;
    public Credits() {
    }
}
