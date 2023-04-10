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
import org.springframework.util.StringUtils;

import java.util.*;

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

    public void activate(Long id, String code){
        Credits credits = creditsRepository.findById(id).get();
        if(isActivated(code)){
            credits.setVerified(true);
        }
        creditsRepository.save(credits);
    }

    public void sign_up(UserPostModel user) {
        if (getByLogin(user.getEmail()).isPresent()) {
            throw new RuntimeException();
        }

        Credits credits = UserMapper.toCredits(user);
        credits.setActivationCode(UUID.randomUUID().toString());
        credits.setVerified(false);
        creditsRepository.save(credits);
        if (user.getRole().equals(Role.LODGER.getAuthority())) {
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
        if (!StringUtils.isEmpty(credits.getEmail())) {
            String message = "china";
        }

        mailSender.send(credits.getEmail(),"REG","Sanchi");
    }

    private boolean isActivated(String code) {
        return getAllCredits().stream().anyMatch(credits -> credits.getActivationCode().equals(code));
    }

    private List<Credits> getAllCredits() {
        List<Credits> creditsList = new ArrayList<>();
        creditsRepository.findAll().forEach(credits -> creditsList.add(setRoleToUser(credits)));
        return creditsList;
    }

    private Credits setRoleToUser(Credits credits) {
        leaseholdersRepository.findAll().forEach(leaseholders -> {
            if (leaseholders.getCredits().equals(credits)) {
                credits.setRoles(Collections.singleton(Role.LODGER));
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
