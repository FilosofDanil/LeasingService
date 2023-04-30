package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.profilemodels.ProfileModel;
import com.example.wohnungsuchen.services.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class CredentialsController {
    private final CredentialsService credentialsService;

    @DeleteMapping("/{id}")
    public void deleteCredentials(@PathVariable Long id) {
        credentialsService.deleteCredentials(id);
    }

    @GetMapping("/{id}")
    public ProfileModel getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(credentialsService.getProfileById(id)).getBody();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
