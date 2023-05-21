package com.example.wohnungsuchen.services.security;

import com.example.wohnungsuchen.repositories.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final CredentialsRepository credentialsRepository;
}
