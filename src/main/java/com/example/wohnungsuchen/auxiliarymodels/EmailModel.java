package com.example.wohnungsuchen.auxiliarymodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class EmailModel {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
