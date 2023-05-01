package com.example.wohnungsuchen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.Set;

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
    @Column(name = "title", nullable = false)
    @NotBlank
    @Length(min = 10)
    private String title;
    @Column(name = "post_date", nullable = false)
    @PastOrPresent
    @NotNull
    private Date post_date;
    @Column(name = "cold_arend", nullable = false)
    @Min(0)
    @NotNull
    private Double coldArend;
    @Column(name = "warm_arend", nullable = false)
    @Min(0)
    @NotNull
    private Double warmArend;
    @Column(name = "description")
    private String description;
    @Column(name = "city", nullable = false)
    @NotNull
    @Length(min = 2)
    private String city;
    @Column(name = "address", nullable = false)
    @NotBlank
    private String address;
    @Column(name = "rooms", nullable = false)
    @NotNull
    @Min(0)
    private Integer rooms;
    @Column(name = "area", nullable = false)
    @NotNull
    @Min(0)
    private Double area;
    @Column(name = "internet")
    private Boolean internet;
    @Column(name = "balkoon")
    private Boolean balkoon;
    @Column(name = "floor", nullable = false)
    @NotNull
    @Min(0)
    private Integer floor;
    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Images image;
    @ManyToOne
    @JoinColumn(name = "leaseholder_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Leaseholders leaseholders;
    @JsonIgnore
    @OneToMany(mappedBy = "offer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Liked> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "offer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Appointments> appointments;

    public Offers() {
    }
}
