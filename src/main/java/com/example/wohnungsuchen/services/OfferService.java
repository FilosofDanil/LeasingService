package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.mappers.OfferMapper;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.repositories.OffersRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfferService {
    private final OffersRepository offersRepository;

    public OfferService(OffersRepository offersRepository) {
        this.offersRepository = offersRepository;
    }

    public List<OfferModel> getAllOffers(){
        List<OfferModel> offerModels = new ArrayList<>();
        offersRepository.findAll().forEach(offer -> {
            offerModels.add(OfferMapper.toModel(offer));
        });
        return offerModels;
    }
}
