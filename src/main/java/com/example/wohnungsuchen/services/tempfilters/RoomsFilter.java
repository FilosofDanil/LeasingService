package com.example.wohnungsuchen.services.tempfilters;

import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.services.tempfilters.IFilter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RoomsFilter implements IFilter {
    @Override
    public List<Offers> doFilter(HashMap<String, String> params, List<Offers> offers) {
        Integer roomsFrom = Integer.parseInt(params.get("roomsFrom"));
        Integer roomsTo = Integer.parseInt(params.get("roomsTo"));
        return offers.stream()
                .filter(offer -> offer.getRooms() <= roomsTo && offer.getRooms() >= roomsFrom)
                .collect(Collectors.toList());
    }
}
