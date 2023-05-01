package com.example.wohnungsuchen.models.profilemodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileLeaseHolderModel extends ProfileModel {
    public ProfileLeaseHolderModel(Long id, String name, String surname, String phone, String email, Boolean verified, Date date_of_birth) {
        super(id, name, surname, phone, email, verified, date_of_birth);
    }

    @JsonProperty("offers_link")
    private String offersLink;

    @JsonProperty("profile")
    private final String profile = "leaseholder";
}
