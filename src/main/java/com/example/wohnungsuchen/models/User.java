package com.example.wohnungsuchen.models;

import com.example.wohnungsuchen.auth.Role;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;
    private String profile_password;
    private String profile_name;
    private String surname;
    private Set<Role> roles;

}
