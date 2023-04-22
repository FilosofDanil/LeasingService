package com.example.wohnungsuchen.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LikeModel {
    @JsonProperty("offer_title")
    private String offerTitle;
    @JsonProperty("offer_image_path")
    private String imagePath;
    @JsonProperty("offer_city")
    private String offerCity;
    @JsonProperty("offer_address")
    private String offerAddress;
    @JsonProperty("username")
    private String username;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("profile_link")
    private String profileLink;
    @JsonProperty("appointment_link")
    private String appointmentLink;
}
