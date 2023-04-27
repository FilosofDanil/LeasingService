package com.example.wohnungsuchen.sorters.offersorters;

import com.example.wohnungsuchen.entities.Offers;

import java.util.Comparator;

public class OfferRoomsComparator implements Comparator<Offers> {
    private final Direction direction;

    public OfferRoomsComparator(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int compare(Offers o1, Offers o2) {
        if (direction.getDirection().equals("ASC")) {
            if (o1.getRooms() > o2.getRooms()) {
                return 1;
            }
            if (o1.getRooms() < o2.getRooms()) {
                return -1;
            }
        }
        if (direction.getDirection().equals("DESC")) {
            if (o1.getRooms() < o2.getRooms()) {
                return 1;
            }
            if (o1.getRooms() > o2.getRooms()) {
                return -1;
            }
        }
        return 0;
    }
}
