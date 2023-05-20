package com.example.wohnungsuchen.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @JsonProperty("likes_link")
    private String likes_link;

    @JsonProperty("self_link")
    private String self_link;
}
