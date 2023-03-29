package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.Role;
import com.example.wohnungsuchen.entities.Credits;
import com.example.wohnungsuchen.mappers.UserMapper;
import com.example.wohnungsuchen.models.User;
import com.example.wohnungsuchen.repositories.CreditsRepository;
import com.example.wohnungsuchen.repositories.LodgersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CreditsService {
    private final CreditsRepository creditsRepository;
    private final LodgersRepository lodgersRepository;
    private final SearchersRepository searchersRepository;

    public Optional<User> getByLogin(@NonNull String login) {
        return getAllCredits()
                .stream()
                .filter(credits -> login.equals(credits.getEmail()))
                .findFirst();
    }

    private List<User> getAllCredits() {
        List<User> creditsList = new ArrayList<>();
        creditsRepository.findAll().forEach(credits -> {
            creditsList.add(setRoleToUser(credits, UserMapper.format(credits)));
        });
        return creditsList;
    }

    private User setRoleToUser(Credits credits, User user) {
        lodgersRepository.findAll().forEach(lodgers -> {
            if (lodgers.getCredits().equals(credits)) {
                user.setRoles(Collections.singleton(Role.LODGER));
            }
        });
        searchersRepository.findAll().forEach(searchers -> {
            if (searchers.getCredits().equals(credits)) {
                user.setRoles(Collections.singleton(Role.SEARCHER));
            }
        });
        return user;
    }
}
