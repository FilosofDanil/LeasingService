package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auth.JwtAuthentication;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.models.CreatedOfferModel;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.services.AuthService;
import com.example.wohnungsuchen.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;
    private final AuthService authService;

    @GetMapping("/v1")
    public List<OfferModel> getAllOffers(@RequestParam(required = false) String filter) throws ParseException {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        if (filter == null) {
            return ResponseEntity.ok(offerService.getAllOffers()).getBody();
        }
        return ResponseEntity.ok(offerService.getAllOffers(filter)).getBody();
    }

    @GetMapping("/v2")
    public Page<OfferModel> getAllOffers(@RequestParam(required = false) String filter, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable) throws ParseException {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok(offerService.getAllOffers(pageable, filter)).getBody();
    }

    @GetMapping("/v3")
    public Page<OfferModel> getAllOffers(@RequestParam(required = false) String sort, @RequestParam(required = false) String direction, @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) throws ParseException {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok(offerService.getAllOffersPage(pageable)).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/v1/{leaseholder_id}")
    public List<CreatedOfferModel> getAllCreatedOffersByLeaseholder(@PathVariable Long leaseholder_id) {
        return ResponseEntity.ok(offerService.getAllCreatedOffersByLeaseholderId(leaseholder_id)).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/{id}")
    public OfferModel getOfferById(@PathVariable Long id) {
        return null;
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PostMapping("/")
    public ResponseEntity<Offers> addOffer(@RequestBody OfferPostModel offerPostModel) {
        Offers savedOffer = offerService.addOffer(offerPostModel, SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PutMapping("/{id}")
    public void updateOffer(@PathVariable Long id, @RequestBody OfferPostModel offerPostModel) {
        offerService.updateOffer(id, offerPostModel, SecurityContextHolder.getContext().getAuthentication());
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PatchMapping("/{id}")
    public void partlyUpdateOffer(@PathVariable Long id, @RequestBody OfferPostModel offerPostModel) {
        offerService.partlyUpdateOffer(id, offerPostModel);
    }
}
