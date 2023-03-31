package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.mappers.OfferMapper;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.repositories.ImagesRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfferService {
    private final OffersRepository offersRepository;
    private final ImagesRepository imagesRepository;

    public OfferService(OffersRepository offersRepository, ImagesRepository imagesRepository) {
        this.offersRepository = offersRepository;
        this.imagesRepository = imagesRepository;
    }

    public List<OfferModel> getAllOffers() {
        List<OfferModel> offerModels = new ArrayList<>();
        offersRepository.findAll().forEach(offer -> {
            offerModels.add(OfferMapper.toModel(offer));
        });
        return offerModels;
    }

    public List<OfferModel> getOfferByCity(String city) {
        List<OfferModel> offerModels = new ArrayList<>();
        offersRepository.findAll().forEach(offer -> {
            if (offer.getCity().equals(city)) {
                offerModels.add(OfferMapper.toModel(offer));
            }
        });
        return offerModels;
    }

    public void addOffer(OfferPostModel offerPostModel) {
        Offers offer = OfferMapper.toOffer(offerPostModel);
        imagesRepository.findAll().forEach(images -> {
            if (offerPostModel.getLink().equals(images.getLink())) {
                offer.setImage(images);
            }
        });
        offersRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        offersRepository.deleteById(id);
    }

    public void updateOffer(Long id, OfferPostModel offer) {
        offersRepository.findById(id).map(offers -> {
                    offers.setAddress(offer.getCity());
                    offers.setArea(offer.getArea());
                    offers.setBalkoon(offer.getBalkoon());
                    offers.setCity(offer.getCity());
                    offers.setColdArend(offer.getColdArend());
                    offers.setWarmArend(offer.getWarmArend());
                    offers.setRooms(offer.getRooms());
                    offers.setFloor(offer.getFloor());
                    offers.setDescription(offer.getDescription());
                    offers.setInternet(offer.getInternet());
                    offers.setTitle(offer.getTitle());
                    return offersRepository.save(offers);
                })
                .orElseGet(() ->{
                    Offers offers = OfferMapper.toOffer(offer);
                    offers.setId(id);
                    return offersRepository.save(offers);
                });
    }

    public void partlyUpdateOffer() {

    }

    private void sort() {

    }
}
