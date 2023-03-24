package com.example.wohnungsuchen.mappers;

import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.models.OfferModel;

public class OfferMapper {
    public static OfferModel toModel(Offers offer){
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
}
