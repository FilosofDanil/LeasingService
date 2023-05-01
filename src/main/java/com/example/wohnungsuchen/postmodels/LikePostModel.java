package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LikePostModel {
    @JsonProperty("user_id")
    @NotNull
    private Long user_id;
    @JsonProperty("offer_id")
    @NotNull
    private Long offer_id;
}
