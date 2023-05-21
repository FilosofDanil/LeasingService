package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.*;
import com.example.wohnungsuchen.models.auxiliarymodels.EmailModel;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.postmodels.UserPostModel;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CredentialsService credentialsService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final Credentials credentials = credentialsService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User Not Found"));
        if (credentials.getPassword().equals(authRequest.getPassword()) && credentials.getVerified()) {
            final String accessToken = jwtProvider.generateAccessToken(credentials);
            final String refreshToken = jwtProvider.generateRefreshToken(credentials);
            refreshStorage.put(credentials.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid password or your account is not verified");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Credentials credentials = credentialsService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User Not Found"));
                final String accessToken = jwtProvider.generateAccessToken(credentials);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Credentials credentials = credentialsService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User Not Found"));
                final String accessToken = jwtProvider.generateAccessToken(credentials);
                final String newRefreshToken = jwtProvider.generateRefreshToken(credentials);
                refreshStorage.put(credentials.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT-token");
    }

    public void signup(UserPostModel userPostModel) {
        credentialsService.sign_up(userPostModel);
    }

    public void activate(String code) {
        credentialsService.activate(code);
    }

    public void sendActivationCode(EmailModel email) {
        if (credentialsService.getByLogin(email.getEmail()).isPresent()) {
            credentialsService.sendActivationCode(email);
        } else {
            throw new NullPointerException();
        }

    }

    public JwtAuthentication getAuthInfo() {
        if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User username = (User) auth.getPrincipal();
            String name = auth.getName();
            java.util.List<Role> roles = (java.util.List<Role>) auth.getAuthorities();
            Set<Role> setRoles = new HashSet<>();
            setRoles.addAll(roles);
            return new JwtAuthentication(username.getUsername(), name, setRoles);
        }
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();

    }

}
