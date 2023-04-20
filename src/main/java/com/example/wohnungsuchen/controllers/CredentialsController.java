package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.ProfileModel;
import com.example.wohnungsuchen.services.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class CredentialsController {
    private final CredentialsService credentialsService;

    @DeleteMapping("/v1/{id}")
    public void deleteCredentials(@PathVariable Long id) {
        credentialsService.deleteCredentials(id);
    }

    @GetMapping("/v1/{id}")
    public ProfileModel getProfile(@PathVariable Long id) {
        return credentialsService.getProfileById(id);
    }
}
