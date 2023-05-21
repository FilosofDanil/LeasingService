package com.example.wohnungsuchen.services.tempfilters;

import com.example.wohnungsuchen.entities.Offers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AreaFilter implements IFilter {
    @Override
    public List<Offers> doFilter(HashMap<String, String> params, List<Offers> offers) {
        Double areaFrom = Double.parseDouble(params.get("areaFrom"));
        Double areaTo = Double.parseDouble(params.get("areaTo"));
        return offers.stream()
                .filter(offer -> offer.getArea() <= areaTo && offer.getArea() >= areaFrom)
                .collect(Collectors.toList());
    }
}
