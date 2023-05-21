package com.example.wohnungsuchen.services.tempfilters;

import java.util.HashMap;

public class FilterFactory {
    private HashMap<String, IFilter> filterHashMap;

    public FilterFactory() {
        init();
    }

    public IFilter getFilter(String filter) {
        return filterHashMap.get(filter);
    }

    private void init() {
        filterHashMap = new HashMap<>();
        filterHashMap.put("area", new AreaFilter());
        filterHashMap.put("city", new CityFilter());
        filterHashMap.put("date", new DateFilter());
        filterHashMap.put("rooms", new RoomsFilter());
        filterHashMap.put("cost", new CostFilter());
    }
}
