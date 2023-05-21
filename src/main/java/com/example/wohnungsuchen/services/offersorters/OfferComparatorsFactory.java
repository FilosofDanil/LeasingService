package com.example.wohnungsuchen.services.offersorters;

import com.example.wohnungsuchen.entities.Offers;

import java.util.Comparator;
import java.util.HashMap;

public class OfferComparatorsFactory {
    private HashMap<String, Comparator<Offers>> sorterHashMap;

    public OfferComparatorsFactory() {
        init();
    }

    public Comparator<Offers> getSorter(String key) {
        return sorterHashMap.get(key);
    }

    private void init() {
        sorterHashMap = new HashMap<>();
        sorterHashMap.put("dateDESC", new OfferDateComparator(Direction.DESC));
        sorterHashMap.put("dateASC", new OfferDateComparator(Direction.ASC));
        sorterHashMap.put("priceDESC", new OfferPriceComparator(Direction.DESC));
        sorterHashMap.put("priceASC", new OfferPriceComparator(Direction.ASC));
        sorterHashMap.put("areaDESC", new OfferAreaComparator(Direction.DESC));
        sorterHashMap.put("areaASC", new OfferAreaComparator(Direction.ASC));
        sorterHashMap.put("roomsDESC", new OfferRoomsComparator(Direction.DESC));
        sorterHashMap.put("roomsASC", new OfferRoomsComparator(Direction.ASC));
    }
}
