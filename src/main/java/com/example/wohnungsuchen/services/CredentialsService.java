package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auth.Role;
import com.example.wohnungsuchen.models.auxiliarymodels.EmailModel;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.exeptions.AuthException;
import com.example.wohnungsuchen.exeptions.RegistryException;
import com.example.wohnungsuchen.exeptions.VerifyException;
import com.example.wohnungsuchen.models.profilemodels.InvitedModel;
import com.example.wohnungsuchen.models.profilemodels.ProfileLeaseHolderModel;
import com.example.wohnungsuchen.models.profilemodels.ProfileModel;
import com.example.wohnungsuchen.models.profilemodels.ProfileSearcherModel;
import com.example.wohnungsuchen.postmodels.UserPostModel;
import com.example.wohnungsuchen.repositories.CredentialsRepository;
import com.example.wohnungsuchen.repositories.LeaseholdersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import com.example.wohnungsuchen.services.security.MailSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

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
            if (credentials == null) {
                throw new IllegalArgumentException();
            }
            credentials.setVerified(true);
            credentials.setActivationCode(null);
            credentialsRepository.save(credentials);
        }
    }

    public void sign_up(UserPostModel user) {
        if (getByLogin(user.getEmail()).isPresent()) {
            throw new RegistryException();
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
                    .notifications(true)
                    .build();
            searchersRepository.save(searcher);
        }
        setRoleToUser(credentials);
        sendActivationCodeAssistant(credentials);
    }

    public void sendActivationCode(EmailModel email) {
        Credentials credentials = credentialsRepository.findByEmail(email.getEmail());
        if (credentials.getVerified()) {
            throw new VerifyException("Already verified");
        }
        if (credentials.getActivationCode() == null) {
            credentials.setActivationCode(UUID.randomUUID().toString());
            setRoleToUser(credentials);
            credentialsRepository.save(credentials);
        }
        sendActivationCodeAssistant(credentials);
    }

    private void sendActivationCodeAssistant(Credentials credentials) {
        mailSender.send(credentials.getEmail(), "Profile Verification", "Dear " + credentials.getProfile_name() + " " + credentials.getSurname() + "\n" +
                "We're glad to see, that you have chosen our service. \n" +
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

    public void deleteCredentials(Long id, Authentication auth) {
        if (credentialsRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Failed to delete");
        }

        String username;
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            User user = (User) auth.getPrincipal();
            username = user.getUsername();
        } else {
            username = (String) auth.getPrincipal();
        }
        if (getByLogin(username).isEmpty()) {
            throw new NullPointerException();
        }
        Credentials comparedCredentials = getByLogin(username).get();
        if (!Objects.equals(comparedCredentials.getId(), id)) {
            throw new AuthException("Trying to delete not own profile!");
        }
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
        if (credentialsRepository.findById(id).isEmpty()) {
            throw new NotFoundException("");
        }
        Credentials credentials = credentialsRepository.findById(id).get();
        Object o = null;
        if (searchersRepository.findSearchersByCredentials(credentials) != null) {
            o = searchersRepository.findSearchersByCredentials(credentials);
        }
        if (leaseholdersRepository.findByCredentials(credentials) != null) {
            o = leaseholdersRepository.findByCredentials(credentials);
        }
        return UserMapper.toProfileModel(credentials, o);
    }

    public List<InvitedModel> getProfilesAssignedToCertainAppointment(Integer appointmentId) {
        List<Credentials> credentialsList = new ArrayList<>();
        searchersRepository.findAllByAssignments(appointmentId).forEach(searchers -> {
            credentialsList.add(searchers.getCredentials());
        });
        return credentialsList.stream().map(UserMapper::toInvitedModel).collect(Collectors.toList());
    }

    static class UserMapper {
        private static InvitedModel toInvitedModel(Credentials credentials) {
            return InvitedModel.builder()
                    .email(credentials.getEmail())
                    .phone(credentials.getPhone())
                    .name(credentials.getProfile_name())
                    .surname(credentials.getSurname())
                    .build();
        }

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

        private static ProfileModel toProfileModel(Credentials credentials, Object o) {
            ProfileModel profileModel = ProfileModel.builder()
                    .id(credentials.getId())
                    .name(credentials.getProfile_name())
                    .surname(credentials.getSurname())
                    .phone(credentials.getPhone())
                    .email(credentials.getEmail())
                    .verified(credentials.getVerified())
                    .date_of_birth(credentials.getBirthDate())
                    .build();
            if (o instanceof Searchers searchers) {
                String link = "disable";
                if (searchers.getNotifications()) {
                    link = "enable";
                }
                ProfileSearcherModel profileSearcherModel = new ProfileSearcherModel(profileModel.getId(), profileModel.getName(), profileModel.getSurname(), profileModel.getPhone(), profileModel.getEmail(), profileModel.getVerified(), profileModel.getDate_of_birth());
                profileSearcherModel.setNotificationLink("http://localhost:8080/api/searchers/" + link + "/" + searchers.getId());
                return profileSearcherModel;
            }
            if (o instanceof Leaseholders leaseholders) {
                ProfileLeaseHolderModel profileLeaseHolderModel = new ProfileLeaseHolderModel(profileModel.getId(), profileModel.getName(), profileModel.getSurname(), profileModel.getPhone(), profileModel.getEmail(), profileModel.getVerified(), profileModel.getDate_of_birth());
                profileLeaseHolderModel.setOffersLink("http://localhost:8080/api/offers/" + leaseholders.getId());
                return profileLeaseHolderModel;
            }
            return profileModel;
        }
    }
}
