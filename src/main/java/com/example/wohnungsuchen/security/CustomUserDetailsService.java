package com.example.wohnungsuchen.security;

import com.example.wohnungsuchen.repositories.CreditsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final CreditsRepository creditsRepository;
}
