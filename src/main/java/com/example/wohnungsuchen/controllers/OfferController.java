package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auth.JwtAuthentication;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.services.AuthService;
import com.example.wohnungsuchen.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/offers")
public class OfferController {
    private final OfferService offerService;
    private final AuthService authService;

    public OfferController(OfferService offerService, AuthService authService) {
        this.offerService = offerService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('SEARCHER')")
    @GetMapping("/")
    public List<OfferModel>getAllOffers(){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok(offerService.getAllOffers()).getBody();
    }

    @GetMapping("/{id}")
    public OfferModel getOfferById(@PathVariable Long id){
        return null;
    }
    @GetMapping("/city={city_name}")
    public List<OfferModel>getOffersByCity(@PathVariable String city_name){
        return null;
    }
    @PostMapping("/")
    public void addOffer(@RequestBody OfferPostModel offerPostModel){

    }
    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id){

    }
    @PutMapping("/{id}")
    public void updateOffer(@PathVariable Long id){

    }
    @PatchMapping
    public void partlyUpdateOffer(@PathVariable Long id){

    }
}
