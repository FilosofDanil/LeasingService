package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auth.JwtRequest;
import com.example.wohnungsuchen.auth.JwtResponse;
import com.example.wohnungsuchen.auth.RefreshJwtRequest;
import com.example.wohnungsuchen.postmodels.UserPostModel;
import com.example.wohnungsuchen.services.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    public void signup(@RequestBody UserPostModel userPostModel){
        authService.signup(userPostModel);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @GetMapping("activate/{id}/{code}")
    public void activate(@PathVariable String code, @PathVariable Long id){
        authService.activate(id,code);
    }

}
