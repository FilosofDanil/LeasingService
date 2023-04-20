package com.example.wohnungsuchen.filters;

import com.example.wohnungsuchen.entities.Offers;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CityFilter implements IFilter {
    @Override
    public List<Offers> doFilter(HashMap<String, String> params, List<Offers> offers) {
        String city = params.get("city");
        return offers.stream()
                .filter(offer -> offer.getCity().equals(city))
                .collect(Collectors.toList());
    }
}