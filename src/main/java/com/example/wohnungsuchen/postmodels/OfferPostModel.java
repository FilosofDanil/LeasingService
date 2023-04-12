package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OfferPostModel {
    @JsonProperty("title")
    private String title;

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

    @JsonProperty("leaseholder_id")
    private Long leaseholder_id;
}
