package com.example.wohnungsuchen.sorters.offersorters;

import com.example.wohnungsuchen.entities.Offers;

import java.util.Comparator;

public class OfferDateComparator implements Comparator<Offers> {
    private final Direction direction;

    public OfferDateComparator(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int compare(Offers o1, Offers o2) {
        if (direction.getDirection().equals("ASC")) {
            if (o1.getPost_date().after(o2.getPost_date())) {
                return 1;
            }
            if (o2.getPost_date().after(o1.getPost_date())) {
                return -1;
            }
        }
        if (direction.getDirection().equals("DESC")) {
            if (o1.getPost_date().before(o2.getPost_date())) {
                return 1;
            }
            if (o2.getPost_date().before(o1.getPost_date())) {
                return -1;
            }
        }
        return 0;
    }
}
