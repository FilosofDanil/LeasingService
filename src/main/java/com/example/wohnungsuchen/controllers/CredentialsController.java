package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.services.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class CredentialsController {
    private final CredentialsService credentialsService;
    @DeleteMapping("/{id}")
    public void deleteCredentials(@PathVariable Long id){
        credentialsService.deleteCredentials(id);
    }
}
