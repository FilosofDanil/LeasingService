package com.example.wohnungsuchen.services.tempfilters;

import com.example.wohnungsuchen.entities.Offers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DateFilter implements IFilter {
    @Override
    public List<Offers> doFilter(HashMap<String, String> params, List<Offers> offers) throws ParseException {
        Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(params.get("dateFrom"));
        Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(params.get("dateTo"));

        return offers.stream()
                .filter(offer -> offer.getPost_date().before(dateTo) && offer.getPost_date().after(dateFrom))
                .collect(Collectors.toList());
    }
}

