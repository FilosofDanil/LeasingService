package com.example.wohnungsuchen.mappers;

import com.example.wohnungsuchen.entities.Credits;
import com.example.wohnungsuchen.models.User;
import com.example.wohnungsuchen.postmodels.UserPostModel;

public class UserMapper {
    public static User format(Credits credits){
        return User.builder()
                .profile_name(credits.getProfile_name())
                .profile_password(credits.getPassword())
                .surname(credits.getSurname())
                .email(credits.getEmail())
                .build();
    }

    public static Credits toCredits(UserPostModel user){
        return Credits.builder()
                .profile_name(user.getProfile_name())
                .email(user.getEmail())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .verified(false)
                .build();
    }
}
