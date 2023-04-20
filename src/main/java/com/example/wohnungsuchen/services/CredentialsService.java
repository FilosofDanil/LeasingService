package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.Role;
import com.example.wohnungsuchen.auxiliarymodels.EmailModel;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.models.ProfileModel;
import com.example.wohnungsuchen.postmodels.UserPostModel;
import com.example.wohnungsuchen.repositories.CredentialsRepository;
import com.example.wohnungsuchen.repositories.LeaseholdersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import com.example.wohnungsuchen.security.MailSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final LeaseholdersRepository leaseholdersRepository;
    private final SearchersRepository searchersRepository;

    @Autowired
    private MailSender mailSender;

    public Optional<Credentials> getByLogin(@NonNull String login) {
        return getAllCredits()
                .stream()
                .map(this::setRoleToUser)
                .filter(credits -> login.equals(credits.getEmail()))
                .findFirst();
    }

    public void activate(String code) {
        if (isActivated(code)) {
            Credentials credentials = credentialsRepository.findCreditsByActivationCode(code);
            credentials.setVerified(true);
            credentials.setActivationCode(null);
            credentialsRepository.save(credentials);
        }
    }

    public void sign_up(UserPostModel user) {
        if (getByLogin(user.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        Credentials credentials = UserMapper.toCredits(user);
        credentials.setActivationCode(UUID.randomUUID().toString());
        credentials.setVerified(false);
        credentialsRepository.save(credentials);
        if (user.getRole().equals(Role.LEASEHOLDER.getAuthority())) {
            Leaseholders lodger = Leaseholders.builder()
                    .credentials(credentials)
                    .build();
            leaseholdersRepository.save(lodger);
            return;
        }
        if (user.getRole().equals(Role.SEARCHER.getAuthority())) {
            Searchers searcher = Searchers.builder()
                    .credentials(credentials)
                    .build();
            searchersRepository.save(searcher);
        }
        setRoleToUser(credentials);
        sendActivationCodeAssistant(credentials);
    }

    public void sendActivationCode(EmailModel email) {
        Credentials credentials = credentialsRepository.findByEmail(email.getEmail());
        if (credentials.getVerified()) {
            throw new RuntimeException();
        }
        if (credentials.getActivationCode() == null) {
            credentials.setActivationCode(UUID.randomUUID().toString());
            setRoleToUser(credentials);
            credentialsRepository.save(credentials);
        }
        sendActivationCodeAssistant(credentials);
    }

    private void sendActivationCodeAssistant(Credentials credentials) {
        mailSender.send(credentials.getEmail(), "Profile Verification", "Hi! We're glad to see, that you have chosen our service. \n" +
                "For a further partnership with you, it's required to verify your email \n" +
                "To activate your account, you just need to click the link below \n"
                + "http://localhost:8080/api/auth/activate/" + credentials.getActivationCode());
    }

    private boolean isActivated(String code) {
        return getAllCredits().stream().filter(credits -> credits.getActivationCode() != null).anyMatch(credits -> credits.getActivationCode().equals(code));
    }

    private List<Credentials> getAllCredits() {
        List<Credentials> credentialsList = new ArrayList<>();
        credentialsRepository.findAll().forEach(credentialsList::add);
        return credentialsList;
    }

    private Credentials setRoleToUser(Credentials credentials) {
        leaseholdersRepository.findAll().forEach(leaseholders -> {
            if (leaseholders.getCredentials().getId().equals(credentials.getId())) {
                credentials.setRoles(new HashSet<>(Set.of(Role.LEASEHOLDER)));
            }
        });
        searchersRepository.findAll().forEach(searchers -> {
            if (searchers.getCredentials().getId().equals(credentials.getId())) {
                credentials.setRoles(new HashSet<>(Set.of(Role.SEARCHER)));
            }
        });
        return credentials;
    }

    public void deleteCredentials(Long id) {
        Credentials credentials = credentialsRepository.findById(id).get();
        leaseholdersRepository.findAll().forEach(leaseholders -> {
            if (leaseholders.getCredentials().equals(credentials)) {
                leaseholdersRepository.delete(leaseholders);
            }
        });
        searchersRepository.findAll().forEach(searchers -> {
            if (searchers.getCredentials().equals(credentials)) {
                searchersRepository.delete(searchers);
            }
        });
        credentialsRepository.deleteById(id);
    }

    public ProfileModel getProfileById(Long id) {
        List<Credentials> credentialsList = getAllCredits();
        if (credentialsList.stream().findAny().isEmpty()) {
            throw new NullPointerException();
        }
        return UserMapper.toProfileModel(credentialsList.stream().findFirst().get());
    }

    static class UserMapper {
//        private static User format(Credentials credentials){
//            return User.builder()
//                    .profile_name(credentials.getProfile_name())
//                    .profile_password(credentials.getPassword())
//                    .surname(credentials.getSurname())
//                    .email(credentials.getEmail())
//                    .build();
//        }


        private static Credentials toCredits(UserPostModel user) {
            return Credentials.builder()
                    .profile_name(user.getProfile_name())
                    .email(user.getEmail())
                    .surname(user.getSurname())
                    .phone(user.getPhone())
                    .password(user.getPassword())
                    .birthDate(user.getBirthDate())
                    .verified(false)
                    .build();
        }

        private static ProfileModel toProfileModel(Credentials credentials) {
            return ProfileModel.builder()
                    .id(credentials.getId())
                    .name(credentials.getProfile_name())
                    .surname(credentials.getSurname())
                    .phone(credentials.getPhone())
                    .email(credentials.getEmail())
                    .verified(credentials.getVerified())
                    .date_of_birth(credentials.getBirthDate())
                    .build();
        }
    }
}
