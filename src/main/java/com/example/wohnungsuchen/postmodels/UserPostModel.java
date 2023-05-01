package com.example.wohnungsuchen.postmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserPostModel {
    @JsonProperty("profile_name")
    @NotBlank
    @Length(min = 3)
    private String profile_name;
    @JsonProperty("surname")
    @NotBlank
    @Length(min = 3)
    private String surname;
    @JsonProperty("phone")
    @Pattern(regexp = "(\\+\\d{3}\\d{9})" +
            "|(\\+\\d{2}\\(\\d{3}\\)\\d{7})|" +
            "(\\(\\d{3}\\)\\d{7})|" +
            "(0\\(\\d{3}\\)\\d{6})")
    @NotBlank
    private String phone;
    @JsonProperty("email")
    @Email
    @NotBlank
    private String email;
    @JsonProperty("date_of_birth")
    @NotNull
    private Date birthDate;
    @JsonProperty("password")
    @NotBlank
    @Length(min = 8)
    private String password;
    @JsonProperty("role")
    @NotBlank
    private String role;
}
