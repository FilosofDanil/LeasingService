package com.example.wohnungsuchen.services.tempfilters;

import com.example.wohnungsuchen.entities.Offers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CostFilter implements IFilter {
    @Override
    public List<Offers> doFilter(HashMap<String, String> params, List<Offers> offers) {
        Double priceFrom = Double.parseDouble(params.get("priceFrom"));
        Double priceTo = Double.parseDouble(params.get("priceTo"));
        return offers.stream()
                .filter(offer -> offer.getWarmArend() <= priceTo && offer.getWarmArend() >= priceFrom)
                .collect(Collectors.toList());
    }
}
