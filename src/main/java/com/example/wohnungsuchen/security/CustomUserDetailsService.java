package com.example.wohnungsuchen.security;

import com.example.wohnungsuchen.repositories.CreditsRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {
    private final CreditsRepository creditsRepository;

    public CustomUserDetailsService(CreditsRepository creditsRepository) {
        this.creditsRepository = creditsRepository;
    }

}
