package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.JwtAuthentication;
import com.example.wohnungsuchen.auth.JwtProvider;
import com.example.wohnungsuchen.auth.JwtRequest;
import com.example.wohnungsuchen.auth.JwtResponse;
import com.example.wohnungsuchen.auxiliarymodels.EmailModel;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.postmodels.UserPostModel;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
            throw new AuthException("Неправильный пароль");
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
        throw new AuthException("Невалидный JWT токен");
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
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
