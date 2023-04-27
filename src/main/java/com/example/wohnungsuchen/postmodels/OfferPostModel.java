package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OfferPostModel {
    @JsonProperty("title")
    @NotBlank
    @Length(min = 10)
    private String title;

    @JsonProperty("cold_arend")
    @Min(0)
    @NotBlank
    private Double coldArend;

    @JsonProperty("warm_arend")
    @Min(0)
    @NotBlank
    private Double warmArend;

    @JsonProperty("description")
    private String description;

    @JsonProperty("city")
    @NotBlank
    @Length(min = 2)
    private String city;

    @JsonProperty("address")
    @NotBlank
    private String address;

    @JsonProperty("rooms")
    @NotBlank
    @Min(0)
    private Integer rooms;

    @JsonProperty("area")
    @NotBlank
    @Min(0)
    private Double area;

    @JsonProperty("internet")
    private Boolean internet;

    @JsonProperty("balkoon")
    private Boolean balkoon;

    @JsonProperty("floor")
    @NotBlank
    @Min(0)
    private Integer floor;

    @JsonProperty("image_link")
    @NotBlank
    private String link;
}
