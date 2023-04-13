package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.LikeModel;
import com.example.wohnungsuchen.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeConroller {
    private final LikeService likedService;

    @PreAuthorize("hasAuthority('SEARCHER')")
    @PostMapping("/")
    public void like(@RequestBody LikeModel likeModel) {
        likedService.like(likeModel);
    }
}
