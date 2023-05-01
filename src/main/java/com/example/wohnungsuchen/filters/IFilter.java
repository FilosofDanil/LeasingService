package com.example.wohnungsuchen.filters;

import com.example.wohnungsuchen.entities.Offers;

import java.text.ParseException;
import java.util.*;

public interface IFilter {
    List<Offers> doFilter(HashMap<String, String> params, List<Offers> offers) throws ParseException;
}
