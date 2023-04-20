package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.LikeModel;
import com.example.wohnungsuchen.postmodels.LikePostModel;
import com.example.wohnungsuchen.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeConroller {
    private final LikeService likedService;

    @PreAuthorize("hasAuthority('SEARCHER')")
    @PostMapping("/")
    public void like(@RequestBody LikePostModel likePostModel) {
        likedService.like(likePostModel);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/v1/{offer_id}")
    public List<LikeModel> getAllLikesByOffer(@PathVariable Long offer_id) {
        return likedService.getAllLikesByOffer(offer_id);
    }
}
