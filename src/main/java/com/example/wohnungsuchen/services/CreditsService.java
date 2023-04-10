package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.Role;
import com.example.wohnungsuchen.entities.Credits;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.postmodels.UserPostModel;
import com.example.wohnungsuchen.repositories.CreditsRepository;
import com.example.wohnungsuchen.repositories.LeaseholdersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import com.example.wohnungsuchen.security.MailSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditsService {
    private final CreditsRepository creditsRepository;
    private final LeaseholdersRepository leaseholdersRepository;
    private final SearchersRepository searchersRepository;

    @Autowired
    private MailSender mailSender;

    public Optional<Credits> getByLogin(@NonNull String login) {
        return getAllCredits()
                .stream()
                .filter(credits -> login.equals(credits.getEmail()))
                .findFirst();
    }

    public void activate(String code) {
        if (isActivated(code)) {
            Credits credits = getAllCredits()
                    .stream()
                    .filter(credit -> credit.getActivationCode() != null)
                    .collect(Collectors.toList())
                    .stream()
                    .filter(credit -> credit.getActivationCode().equals(code))
                    .findFirst().get();
            credits.setVerified(true);
            credits.setActivationCode(null);
            creditsRepository.save(credits);
        }
    }

    public void sign_up(UserPostModel user) {
        if (getByLogin(user.getEmail()).isPresent()) {
            throw new RuntimeException();
        }

        Credits credits = UserMapper.toCredits(user);
        credits.setActivationCode(UUID.randomUUID().toString());
        credits.setVerified(false);
        creditsRepository.save(credits);
        if (user.getRole().equals(Role.LEASEHOLDER.getAuthority())) {
            Leaseholders lodger = Leaseholders.builder()
                    .credits(credits)
                    .build();
            leaseholdersRepository.save(lodger);
            return;
        }
        if (user.getRole().equals(Role.SEARCHER.getAuthority())) {
            Searchers searcher = Searchers.builder()
                    .credits(credits)
                    .build();
            searchersRepository.save(searcher);
        }

        mailSender.send(credits.getEmail(), "Profile Verification", "Hi! We're glad to see, that you have chosen our service. \n" +
                "For a further partnership with you, it's required to verify your email \n" +
                "To activate your account, you just need to click the link below \n"
                + "http://localhost:8080/api/auth/activate/" + credits.getActivationCode());
    }

    private boolean isActivated(String code) {
        return getAllCredits().stream().filter(credits -> credits.getActivationCode() != null).anyMatch(credits -> credits.getActivationCode().equals(code));
    }

    private List<Credits> getAllCredits() {
        List<Credits> creditsList = new ArrayList<>();
        creditsRepository.findAll().forEach(credits -> creditsList.add(setRoleToUser(credits)));
        return creditsList;
    }

    private Credits setRoleToUser(Credits credits) {
        leaseholdersRepository.findAll().forEach(leaseholders -> {
            if (leaseholders.getCredits().equals(credits)) {
                credits.setRoles(Collections.singleton(Role.LEASEHOLDER));
            }
        });
        searchersRepository.findAll().forEach(searchers -> {
            if (searchers.getCredits().equals(credits)) {
                credits.setRoles(Collections.singleton(Role.SEARCHER));
            }
        });
        return credits;
    }

    static class UserMapper {
//        private static User format(Credits credits){
//            return User.builder()
//                    .profile_name(credits.getProfile_name())
//                    .profile_password(credits.getPassword())
//                    .surname(credits.getSurname())
//                    .email(credits.getEmail())
//                    .build();
//        }


        private static Credits toCredits(UserPostModel user) {
            return Credits.builder()
                    .profile_name(user.getProfile_name())
                    .email(user.getEmail())
                    .surname(user.getSurname())
                    .phone(user.getPhone())
                    .password(user.getPassword())
                    .birthDate(user.getBirthDate())
                    .verified(false)
                    .build();
        }
    }
}
