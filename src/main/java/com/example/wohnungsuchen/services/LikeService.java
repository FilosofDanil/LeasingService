package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Liked;
import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.models.LikesModels.LikeLeaseholderModel;
import com.example.wohnungsuchen.models.LikesModels.LikeModel;
import com.example.wohnungsuchen.models.LikesModels.LikeUserModel;
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

    public LikeModel like(Long offer_id, Authentication auth) {
        if (offersRepository.findById(offer_id).isEmpty()) {
            throw new NullPointerException();
        }
        Liked liked = Liked.builder()
                .offer(offersRepository.findById(offer_id).get())
                .searcher(getSearcherByName(auth))
                .build();
        likedRepository.save(liked);
        return LikedMapper.toModel(liked, new Object());
    }

    public List<LikeModel> getAllLikesByOffer(Long offer_id) {
        Object leaseholder = new Leaseholders();
        return likedRepository
                .findAllByOfferId(offer_id)
                .stream()
                .map(liked -> LikedMapper.toModel(liked, leaseholder))
                .collect(Collectors.toList());
    }

    public void cancelLike(Long like_id) {
        likedRepository.deleteById(like_id);
    }

    public List<LikeModel> getAllLikesByUser(Authentication auth) {
        Object searcher = new Searchers();
        List<LikeModel> likeModelList = new ArrayList<>();
        likedRepository.findAll().forEach(liked -> {
            if (liked.getSearcher().getId().equals(getSearcherByName(auth).getId())) {
                likeModelList.add(LikedMapper.toModel(liked, searcher));
            }
        });
        return likeModelList;
    }

    private Searchers getSearcherByName(Authentication auth) {
        String username = (String) auth.getPrincipal();
        List<Credentials> credentials = new ArrayList<>();
        credentialsRepository.findAll().forEach(credentials::add);
        if (credentials.isEmpty()) {
            throw new NullPointerException();
        }
        Credentials cred = credentials.stream().filter(credentials1 -> credentials1.getEmail().equals(username)).findFirst().get();
        return searchersRepository.findSearchersByCredentials(cred);
    }

    static class LikedMapper {
        private static LikeModel toModel(Liked liked, Object o) {
            LikeModel likeModel = LikeModel.builder()
                    .offerAddress(liked.getOffer().getAddress())
                    .imagePath(liked.getOffer().getImage().getLink())
                    .offerCity(liked.getOffer().getCity())
                    .offerTitle(liked.getOffer().getTitle())
                    .username(liked.getSearcher().getCredentials().getProfile_name())
                    .surname(liked.getSearcher().getCredentials().getSurname())
                    .build();
            if (o instanceof Leaseholders) {
                LikeLeaseholderModel likeLeaseholderModel = new LikeLeaseholderModel(likeModel.getOfferTitle(), likeModel.getImagePath(), likeModel.getOfferCity(), likeModel.getOfferAddress(), likeModel.getUsername(), likeModel.getSurname());
                likeLeaseholderModel.setProfileLink(("http://localhost:8080/api/profile/v1/" + liked.getSearcher().getCredentials().getId()));
                likeLeaseholderModel.setAppointmentLink("http://localhost:8080/api/v1/appointments/" + liked.getSearcher().getId());
                return likeLeaseholderModel;
            }
            if (o instanceof Searchers) {
                LikeUserModel likeUserModel = new LikeUserModel(likeModel.getOfferTitle(), likeModel.getImagePath(), likeModel.getOfferCity(), likeModel.getOfferAddress(), likeModel.getUsername(), likeModel.getSurname());
                likeUserModel.setDisableLink("http://localhost:8080//api/likes/v1/" + liked.getId());
                return likeUserModel;
            }
            return likeModel;
        }
    }
}
