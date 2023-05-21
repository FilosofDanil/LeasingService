package com.example.wohnungsuchen.models.auxiliarymodels;

import com.example.wohnungsuchen.entities.Offers;
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
public class AppointmentDeleteModel {
    @JsonProperty("meeting_time")
    private Time meeting_time;
    @JsonProperty("offer")
    private Offers offer;
    @JsonProperty("meeting_date")
    private Date meeting_date;
}
