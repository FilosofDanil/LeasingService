package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Liked;
import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.models.LikeModel;
import com.example.wohnungsuchen.repositories.CredentialsRepository;
import com.example.wohnungsuchen.repositories.LikedRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikedRepository likedRepository;
    private final OffersRepository offersRepository;
    private final SearchersRepository searchersRepository;
    private final CredentialsRepository credentialsRepository;

    public Liked like(Long offer_id, Authentication auth) {
        if (offersRepository.findById(offer_id).isEmpty()) {
            throw new NullPointerException();
        }
        Liked liked = Liked.builder()
                .offer(offersRepository.findById(offer_id).get())
                .searcher(getSearcherByName(auth))
                .build();
        likedRepository.save(liked);
        return liked;
    }

    public List<LikeModel> getAllLikesByOffer(Long offer_id) {
        return likedRepository
                .findAllByOfferId(offer_id)
                .stream()
                .map(LikedMapper::toModel)
                .collect(Collectors.toList());
    }

    public void cancelLike(Long like_id) {
        likedRepository.deleteById(like_id);
    }

    private Searchers getSearcherByName(Authentication auth) {
        String name = auth.getName();
        List<Credentials> credentials = new ArrayList<>();
        credentialsRepository.findAll().forEach(credentials::add);
        Credentials cred = credentials.stream().filter(credentials1 -> credentials1.getProfile_name().equals(name)).findFirst().get();
        return searchersRepository.findSearchersByCredentials(cred);
    }

    static class LikedMapper {
        private static LikeModel toModel(Liked liked) {
            return LikeModel.builder()
                    .offerAddress(liked.getOffer().getAddress())
                    .imagePath(liked.getOffer().getImage().getLink())
                    .offerCity(liked.getOffer().getCity())
                    .offerTitle(liked.getOffer().getTitle())
                    .username(liked.getSearcher().getCredentials().getProfile_name())
                    .surname(liked.getSearcher().getCredentials().getSurname())
                    .profileLink("http://localhost:8080/api/profile/v1/" + liked.getSearcher().getCredentials().getId())
                    .appointmentLink("http://localhost:8080/api/v1/appointments/" + liked.getSearcher().getId())
                    .build();
        }
    }
}
