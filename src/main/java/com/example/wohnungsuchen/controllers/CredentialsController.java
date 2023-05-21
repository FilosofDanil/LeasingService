package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.profilemodels.InvitedModel;
import com.example.wohnungsuchen.models.profilemodels.ProfileModel;
import com.example.wohnungsuchen.services.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class CredentialsController {
    private final CredentialsService credentialsService;

    @DeleteMapping("/{id}")
    public void deleteCredentials(@PathVariable Long id) {
        credentialsService.deleteCredentials(id, SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/{id}")
    public ProfileModel getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(credentialsService.getProfileById(id)).getBody();
    }

    @PreAuthorize("hasAnyAuthority('LEASEHOLDER')")
    @GetMapping("/{appointment_id}/profiles")
    public List<InvitedModel> getAllSearchersAssignedToCertainAppointment(@PathVariable Integer appointment_id) {
        return credentialsService.getProfilesAssignedToCertainAppointment(appointment_id);
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
