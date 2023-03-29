package com.example.wohnungsuchen.mappers;

import com.example.wohnungsuchen.entities.Credits;
import com.example.wohnungsuchen.models.User;

public class UserMapper {
    public static User format(Credits credits){
        return User.builder()
                .profile_name(credits.getProfile_name())
                .profile_password(credits.getPassword())
                .surname(credits.getSurname())
                .email(credits.getEmail())
                .build();
    }
}
