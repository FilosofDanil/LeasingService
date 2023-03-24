package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.services.OfferService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/offers")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public List<OfferModel>getAllOffers(){
        return offerService.getAllOffers();
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
