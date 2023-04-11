package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.repositories.ImagesRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OffersRepository offersRepository;
    private final ImagesRepository imagesRepository;

    public List<OfferModel> getAllOffers() {
        return getOffersList()
                .stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<OfferModel> getOfferByCity(String city) {
        return getOffersList()
                .stream()
                .filter(offer -> offer.getCity().equals(city))
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
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
                .orElseGet(() -> {
                    Offers offers = OfferMapper.toOffer(offer);
                    offers.setId(id);
                    return offersRepository.save(offers);
                });
    }

    public void partlyUpdateOffer() {

    }

    private List<Offers> getOffersList() {
        List<Offers> offers = new ArrayList<>();
        offersRepository.findAll().forEach(offers::add);
        return offers;
    }

    static class OfferMapper {
        private static OfferModel toModel(Offers offer) {
            return OfferModel.builder()
                    .id(offer.getId())
                    .address(offer.getCity())
                    .area(offer.getArea())
                    .balkoon(offer.getBalkoon())
                    .link(offer.getImage().getLink())
                    .city(offer.getCity())
                    .coldArend(offer.getColdArend())
                    .warmArend(offer.getWarmArend())
                    .postdate(offer.getPost_date())
                    .rooms(offer.getRooms())
                    .floor(offer.getFloor())
                    .description(offer.getDescription())
                    .internet(offer.getInternet())
                    .title(offer.getTitle())
                    .build();
        }

        private static Offers toOffer(OfferPostModel offer) {
            return Offers.builder()
                    .address(offer.getCity())
                    .area(offer.getArea())
                    .balkoon(offer.getBalkoon())
                    .city(offer.getCity())
                    .coldArend(offer.getColdArend())
                    .warmArend(offer.getWarmArend())
                    .rooms(offer.getRooms())
                    .floor(offer.getFloor())
                    .description(offer.getDescription())
                    .internet(offer.getInternet())
                    .title(offer.getTitle())
                    .build();
        }
    }
}
