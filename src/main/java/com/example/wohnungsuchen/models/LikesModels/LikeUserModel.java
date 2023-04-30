package com.example.wohnungsuchen.models.LikesModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeUserModel extends LikeModel {
    public LikeUserModel(String offerTitle, String imagePath, String offerCity, String offerAddress, String username, String surname) {
        super(offerTitle, imagePath, offerCity, offerAddress, username, surname);
    }

    @JsonProperty("dislike_link")
    private String disableLink;
}
