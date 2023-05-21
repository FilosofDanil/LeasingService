package com.example.wohnungsuchen.services.filters;

import java.util.*;

public class QueryFilterFactory {
    private HashMap<String, FilterAssistant> filterHashMap;

    public QueryFilterFactory() {
        init();
    }

    public FilterAssistant getFilter(String filter) {
        return filterHashMap.get(filter);
    }

    private void init() {
        filterHashMap = new HashMap<>();
        filterHashMap.put("area", new FilterAssistant(" between ", List.of("areaFrom", "areaTo")));
        filterHashMap.put("city", new FilterAssistant(" = ", List.of("city")));
        filterHashMap.put("post_date", new FilterAssistant(" between ", List.of("dateFrom", "dareTo")));
        filterHashMap.put("rooms", new FilterAssistant(" = ", List.of("rooms")));
        filterHashMap.put("warm_arend", new FilterAssistant(" between ", List.of("costFrom", "costTo")));
    }
}
