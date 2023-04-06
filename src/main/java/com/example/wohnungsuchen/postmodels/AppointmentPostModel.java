package com.example.wohnungsuchen.postmodels;

import com.example.wohnungsuchen.entities.Lodgers;
import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.entities.Searchers;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AppointmentPostModel {
    @JsonProperty("meeting_date")
    private Date meeting_date;
    @JsonProperty("meeting_time")
    private Time meeting_time;
    @JsonProperty("description")
    private String description;
    @JsonProperty("lodger")
    private Lodgers lodger;
    @JsonProperty("offer")
    private Offers offer;
    @JsonProperty("searcher")
    private Searchers searcher;
}
