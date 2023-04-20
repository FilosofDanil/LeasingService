package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Liked;
import com.example.wohnungsuchen.models.LikeModel;
import com.example.wohnungsuchen.postmodels.LikePostModel;
import com.example.wohnungsuchen.repositories.CredentialsRepository;
import com.example.wohnungsuchen.repositories.LikedRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikedRepository likedRepository;
    private final OffersRepository offersRepository;
    private final SearchersRepository searchersRepository;
    private final CredentialsRepository credentialsRepository;

    public void like(LikePostModel likePostModel) {
        Liked liked = Liked.builder()
                .offer(offersRepository.findById(likePostModel.getOffer_id()).get())
                .searcher(searchersRepository.findSearchersByCredentials(credentialsRepository.findById(likePostModel.getUser_id()).get()))
                .build();
        likedRepository.save(liked);
    }

    public List<LikeModel> getAllLikesByOffer(Long offer_id) {
        return likedRepository
                .findAllByOfferId(offer_id)
                .stream()
                .map(LikedMapper::toModel)
                .collect(Collectors.toList());
    }

    static class LikedMapper {
        private static LikeModel toModel(Liked liked) {
            return LikeModel.builder()
                    .username(liked.getSearcher().getCredentials().getProfile_name())
                    .surname(liked.getSearcher().getCredentials().getSurname())
                    .profileLink("http://localhost:8080/api/profile/v1/" + liked.getSearcher().getCredentials().getId())
                    .build();
        }
    }
}
