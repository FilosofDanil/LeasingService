package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.entities.Liked;
import com.example.wohnungsuchen.models.LikesModels.LikeModel;
import com.example.wohnungsuchen.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likedService;

    @PreAuthorize("hasAuthority('SEARCHER')")
    @PostMapping("/{offer_id}")
    public ResponseEntity<Liked> like(@PathVariable Long offer_id) {
        Liked savedLike = likedService.like(offer_id, SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/{offer_id}")
    public List<LikeModel> getAllLikesByOffer(@PathVariable Long offer_id) {
        return ResponseEntity.ok(likedService.getAllLikesByOffer(offer_id)).getBody();
    }

    @PreAuthorize("hasAuthority('SEARCHER')")
    @GetMapping("/")
    public List<LikeModel> getAllLikesByUser() {
        return ResponseEntity.ok(likedService.getAllLikesByUser(SecurityContextHolder.getContext().getAuthentication())).getBody();
    }

    @DeleteMapping("/{like_id}")
    public void cancelLike(@PathVariable Long like_id) {
        likedService.cancelLike(like_id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
