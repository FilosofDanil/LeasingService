package com.example.wohnungsuchen.models;

import com.example.wohnungsuchen.entities.Searchers;
//import com.example.wohnungsuchen.models.profilemodels.InvitedModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AppointmentModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("meeting_date")
    private Date meeting_date;
    @JsonProperty("meeting_time")
    private Time meeting_time;
    @JsonProperty("description")
    private String description;
    @JsonProperty("leaseholder_name")
    private String leaseholder_name;
    @JsonProperty("leaseholder_surname")
    private String leaseholder_surname;
    @JsonProperty("offer_title")
    private String offer_title;
    @JsonProperty("city")
    private String city;
    @JsonProperty("address")
    private String address;
}
