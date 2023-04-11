package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.JwtAuthentication;
import com.example.wohnungsuchen.auth.JwtProvider;
import com.example.wohnungsuchen.auth.JwtRequest;
import com.example.wohnungsuchen.auth.JwtResponse;
import com.example.wohnungsuchen.entities.Credits;
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

    private final CreditsService creditsService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final Credits credits = creditsService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User Not Found"));
        if (credits.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(credits);
            final String refreshToken = jwtProvider.generateRefreshToken(credits);
            refreshStorage.put(credits.getEmail(), refreshToken);
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
                final Credits credits = creditsService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User Not Found"));
                final String accessToken = jwtProvider.generateAccessToken(credits);
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
                final Credits credits = creditsService.getByLogin(login)
                        .orElseThrow(() -> new AuthException("User Not Found"));
                final String accessToken = jwtProvider.generateAccessToken(credits);
                final String newRefreshToken = jwtProvider.generateRefreshToken(credits);
                refreshStorage.put(credits.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public void signup(UserPostModel userPostModel){
        creditsService.sign_up(userPostModel);
    }

    public void activate(String code){
        creditsService.activate(code);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
