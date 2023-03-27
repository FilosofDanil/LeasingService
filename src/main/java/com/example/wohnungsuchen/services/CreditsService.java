package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Credits;
import com.example.wohnungsuchen.repositories.CreditsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CreditsService {
    private final CreditsRepository creditsRepository;

    public Optional<Credits> getByLogin(@NonNull String login){
        return getAllCredits()
                .stream()
                .filter(credits -> login.equals(credits.getEmail()))
                .findFirst();
    }
    private List<Credits> getAllCredits(){
        List<Credits> creditsList = new ArrayList<>();
        creditsRepository.findAll().forEach(creditsList::add);
        return  creditsList;
    }
}
