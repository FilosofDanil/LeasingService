package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auth.JwtAuthentication;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.services.AuthService;
import com.example.wohnungsuchen.services.ImageService;
import com.example.wohnungsuchen.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;
    private final AuthService authService;
    private final ImageService imageService;

    @GetMapping("/")
    public Page<OfferModel> getAllOffers(@RequestParam(required = false) String filter, @PageableDefault(size = 5) Pageable pageable, @RequestParam(required = false) String sort, @RequestParam(required = false) String direction) throws ParseException {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok(offerService.getAllOffers(pageable, filter, sort, direction)).getBody();
    }

    @GetMapping("/{id}")
    public OfferModel getOfferById(@PathVariable Long id) {
        return ResponseEntity.ok(offerService.getOfferById(id)).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/posted/{leaseholder_id}")
    public List<OfferModel> getAllCreatedOffersByLeaseholder(@PathVariable Long leaseholder_id) {
        return ResponseEntity.ok(offerService.getAllCreatedOffersByLeaseholderId(leaseholder_id)).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PostMapping("/")
    public ResponseEntity<OfferModel> addOffer(@RequestBody OfferPostModel offerPostModel, @RequestParam(required = false) MultipartFile file) throws IOException {
        if (file != null) {
            imageService.addFile(file);
        }
        OfferModel savedOffer = offerService.addOffer(offerPostModel, SecurityContextHolder.getContext().getAuthentication());
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
        offerService.partlyUpdateOffer(id, offerPostModel, SecurityContextHolder.getContext().getAuthentication());
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
