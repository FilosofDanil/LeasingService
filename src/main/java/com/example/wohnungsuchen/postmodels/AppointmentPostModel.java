package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AppointmentPostModel {
    @JsonProperty("meeting_date")
    private Date meeting_date;
    @JsonProperty("meeting_time")
    private String meeting_time;
    @JsonProperty("description")
    private String description;
    @JsonProperty("offer")
    private Long offer;
}
