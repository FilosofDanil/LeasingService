package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserPostModel {
    @JsonProperty("profile_name")
    private String profile_name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("date_of_birth")
    private Date birthDate;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private String role;
}
