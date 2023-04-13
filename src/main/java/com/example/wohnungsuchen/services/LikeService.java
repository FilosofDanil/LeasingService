package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Liked;
import com.example.wohnungsuchen.models.LikeModel;
import com.example.wohnungsuchen.repositories.LikedRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikedRepository likedRepository;
    private final OffersRepository offersRepository;
    private final SearchersRepository searchersRepository;

    public void like(LikeModel likeModel) {
        Liked liked = Liked.builder()
                .offer(offersRepository.findById(likeModel.getOffer_id()).get())
                .searcher(searchersRepository.findById(likeModel.getUser_id()).get())
                .build();
        likedRepository.save(liked);
    }
}
