package com.example.wohnungsuchen.models;

import com.example.wohnungsuchen.entities.Offers;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.boot.jaxb.hbm.internal.RepresentationModeConverter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OfferModel {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("postdate")
    private Date postdate;

    @JsonProperty("cold_arend")
    private Double coldArend;

    @JsonProperty("warm_arend")
    private Double warmArend;

    @JsonProperty("description")
    private String description;

    @JsonProperty("city")
    private String city;

    @JsonProperty("address")
    private String address;

    @JsonProperty("rooms")
    private Integer rooms;

    @JsonProperty("area")
    private Double area;

    @JsonProperty("internet")
    private Boolean internet;

    @JsonProperty("balkoon")
    private Boolean balkoon;

    @JsonProperty("floor")
    private Integer floor;

    @JsonProperty("image_link")
    private String link;
}
