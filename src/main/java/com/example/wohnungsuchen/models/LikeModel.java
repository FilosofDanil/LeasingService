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
    @JsonProperty("username")
    private String username;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("profile_link")
    private String profileLink;
//    @JsonProperty("appointment_link")
//    private String appointmentLink;
}
