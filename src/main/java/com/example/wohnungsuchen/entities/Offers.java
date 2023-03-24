package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "offers")
@Setter
@Getter
@AllArgsConstructor
@Builder
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable=false)
    private String title;
    @Column (name="post_date", nullable=false)
    private Date post_date;
    @Column (name="cold_arend", nullable=false)
    private Double coldArend;
    @Column (name="warm_arend", nullable=false)
    private Double warmArend;
    @Column(name="description")
    private String description;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "rooms", nullable = false)
    private Integer rooms;
    @Column(name="area", nullable = false)
    private Double area;
    @Column(name="internet")
    private Boolean internet;
    @Column(name="balkoon")
    private Boolean balkoon;
    @Column(name = "floor", nullable = false)
    private Integer floor;
    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    private Images image;
    public Offers() {
    }
}
