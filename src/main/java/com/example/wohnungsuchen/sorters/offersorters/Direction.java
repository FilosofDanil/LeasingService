package com.example.wohnungsuchen.sorters.offersorters;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {
    ASC("ASC"),
    DESC("DESC");

    private final String direction;

    public String getDirection() {
        return direction;
    }
}
