package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.entities.Liked;
import com.example.wohnungsuchen.models.LikeModel;
import com.example.wohnungsuchen.postmodels.LikePostModel;
import com.example.wohnungsuchen.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeConroller {
    private final LikeService likedService;

    @PreAuthorize("hasAuthority('SEARCHER')")
    @PostMapping("/")
    public ResponseEntity<Liked> like(@RequestBody Long offer_id) {
        Liked savedLike = likedService.like(offer_id, SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/v1/{offer_id}")
    public List<LikeModel> getAllLikesByOffer(@PathVariable Long offer_id) {
        return ResponseEntity.ok(likedService.getAllLikesByOffer(offer_id)).getBody();
    }

    @DeleteMapping("/{like_id}")
    public void cancelLike(@PathVariable Long like_id) {
        likedService.cancelLike(like_id);
    }
}
