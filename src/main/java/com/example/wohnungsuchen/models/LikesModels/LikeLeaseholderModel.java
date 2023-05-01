package com.example.wohnungsuchen.models.LikesModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeLeaseholderModel extends LikeModel{
    public LikeLeaseholderModel(String offerTitle, String imagePath, String offerCity, String offerAddress, String username, String surname) {
        super(offerTitle, imagePath, offerCity, offerAddress, username, surname);
    }

    @JsonProperty("profile_link")
    private String profileLink;
    @JsonProperty("appointment_link")
    private String appointmentLink;
}
