package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auth.JwtAuthentication;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.services.AuthService;
import com.example.wohnungsuchen.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/offers")
public class OfferController {
    private final OfferService offerService;
    private final AuthService authService;

    public OfferController(OfferService offerService, AuthService authService) {
        this.offerService = offerService;
        this.authService = authService;
    }

    @GetMapping("/")
    public List<OfferModel> getAllOffers() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok(offerService.getAllOffers()).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/{id}")
    public OfferModel getOfferById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/city={city_name}")
    public List<OfferModel> getOffersByCity(@PathVariable String city_name) {
        return offerService.getOfferByCity(city_name);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PostMapping("/")
    public void addOffer(@RequestBody OfferPostModel offerPostModel) {
        offerService.addOffer(offerPostModel);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PutMapping("/{id}")
    public void updateOffer(@PathVariable Long id, @RequestBody OfferPostModel offerPostModel) {
        offerService.updateOffer(id, offerPostModel);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PatchMapping("/{id}")
    public void partlyUpdateOffer(@PathVariable Long id, @RequestBody OfferPostModel offerPostModel) {
        offerService.partlyUpdateOffer(id, offerPostModel);
    }
}
