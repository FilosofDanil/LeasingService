package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    private Date meeting_date;
    @JsonProperty("meeting_time")
    @NotBlank
    @Pattern(regexp = "^[0-1][0-9]:[0-5][0-9]:[0-5][0-9]|[1-2][0-3]:[0-5][0-9]:[0-5][0-9]$")
    private String meeting_time;
    @JsonProperty("description")
    @NotBlank
    private String description;
    @JsonProperty("offer")
    @NotBlank
    private Long offer;
}
