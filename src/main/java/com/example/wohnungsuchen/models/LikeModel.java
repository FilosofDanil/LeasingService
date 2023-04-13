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
    @JsonProperty("user_id")
    private Long user_id;
    @JsonProperty("offer_id")
    private Long offer_id;
}
