package com.example.wohnungsuchen.models;

import com.example.wohnungsuchen.entities.Searchers;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    @JsonProperty("lodger_name")
    private String lodger_name;
    @JsonProperty("lodger_surname")
    private String lodger_surname;
    @JsonProperty("offer_title")
    private String offer_title;
    @JsonProperty("city")
    private String city;
    @JsonProperty("address")
    private String address;
}
