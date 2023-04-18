package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.entities.Posted;
import com.example.wohnungsuchen.filters.FilterFactory;
import com.example.wohnungsuchen.filters.IFilter;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.repositories.ImagesRepository;
import com.example.wohnungsuchen.repositories.LeaseholdersRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import com.example.wohnungsuchen.repositories.PostedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OffersRepository offersRepository;
    private final PostedRepository postedRepository;
    private final ImagesRepository imagesRepository;
    private final LeaseholdersRepository leaseholdersRepository;

    public List<OfferModel> getAllOffers(String filter) throws ParseException {
        HashMap<String, String> map = new HashMap<>();
        String[] s = filter.split("\\?");
        String[] filtration = s[0].split(",");
        for (int i = 1; i < s.length; i++) {
            String[] s_pars = s[i].split("=");
            map.put(s_pars[0], s_pars[1]);
        }
        List<Offers> list = doFilter(getOffersList(), filtration, map);
        return list.stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<OfferModel> getAllOffers() {
        List<Offers> list = getOffersList();
        return list.stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    public Page<OfferModel> getAllOffers(Pageable pageable) {
        List<OfferModel> list = getOffersList()
                .stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, 0);
    }

    public List<OfferModel> getOfferByCity(String city) {
        return getOffersList()
                .stream()
                .filter(offer -> offer.getCity().equals(city))
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    public void addOffer(OfferPostModel offerPostModel) {
        Offers offer = OfferMapper.toOffer(offerPostModel);
        imagesRepository.findAll().forEach(images -> {
            if (offerPostModel.getLink().equals(images.getLink())) {
                offer.setImage(images);
            }
        });
        Date postDate = new Date();
        offer.setPost_date(postDate);
        offersRepository.save(offer);
        postedRepository.save(Posted.builder()
                .leaseholder(leaseholdersRepository.findById(offerPostModel.getLeaseholder_id()).get())
                .offer(offer)
                .build());
    }

    private List<Offers> doFilter(List<Offers> offers, String[] filter, HashMap<String, String> params) throws ParseException {
        FilterFactory filterFactory = new FilterFactory();
        for (String s : filter) {
            IFilter filterImpl = filterFactory.getFilter(s);
            offers = filterImpl.doFilter(params, offers);
        }
        return offers;
    }

    public void deleteOffer(Long id) {
        offersRepository.deleteById(id);
    }

    public void updateOffer(Long id, OfferPostModel offer) {
        offersRepository.findById(id).map(offers -> {
                    offers.setAddress(offer.getAddress());
                    offers.setArea(offer.getArea());
                    offers.setBalkoon(offer.getBalkoon());
                    offers.setCity(offer.getCity());
                    offers.setColdArend(offer.getColdArend());
                    offers.setWarmArend(offer.getWarmArend());
                    offers.setRooms(offer.getRooms());
                    offers.setFloor(offer.getFloor());
                    offers.setDescription(offer.getDescription());
                    offers.setInternet(offer.getInternet());
                    offers.setTitle(offer.getTitle());
                    return offersRepository.save(offers);
                })
                .orElseGet(() -> {
                    Offers offers = OfferMapper.toOffer(offer);
                    offers.setId(id);
                    return offersRepository.save(offers);
                });
    }

    public void partlyUpdateOffer(Long id, OfferPostModel offer) {
        offersRepository.findById(id).map(offers -> {
            if (offer.getCity() != null) {
                offers.setCity(offer.getCity());
            }
            if (offer.getArea() != null) {
                offers.setArea(offer.getArea());
            }
            if (offer.getBalkoon() != null) {
                offers.setBalkoon(offer.getBalkoon());
            }
            if (offer.getColdArend() != null) {
                offers.setColdArend(offer.getColdArend());
            }
            if (offer.getAddress() != null) {
                offers.setAddress(offer.getAddress());
            }
            if (offer.getRooms() != null) {
                offers.setRooms(offer.getRooms());
            }
            if (offer.getFloor() != null) {
                offers.setFloor(offer.getFloor());
            }
            if (offer.getDescription() != null) {
                offers.setDescription(offer.getDescription());
            }
            if (offer.getInternet() != null) {
                offers.setInternet(offer.getInternet());
            }
            if (offer.getTitle() != null) {
                offers.setTitle(offer.getTitle());
            }
            return offersRepository.save(offers);
        });
    }

    private List<Offers> getOffersList() {
        List<Offers> offers = new ArrayList<>();
        offersRepository.findAll().forEach(offers::add);
        return offers;
    }

    static class OfferMapper {
        private static OfferModel toModel(Offers offer) {
            return OfferModel.builder()
                    .id(offer.getId())
                    .address(offer.getAddress())
                    .area(offer.getArea())
                    .balkoon(offer.getBalkoon())
                    .link(offer.getImage().getLink())
                    .city(offer.getCity())
                    .coldArend(offer.getColdArend())
                    .warmArend(offer.getWarmArend())
                    .postdate(offer.getPost_date())
                    .rooms(offer.getRooms())
                    .floor(offer.getFloor())
                    .description(offer.getDescription())
                    .internet(offer.getInternet())
                    .title(offer.getTitle())
                    .build();
        }

        private static Offers toOffer(OfferPostModel offer) {
            return Offers.builder()
                    .address(offer.getAddress())
                    .area(offer.getArea())
                    .balkoon(offer.getBalkoon())
                    .city(offer.getCity())
                    .coldArend(offer.getColdArend())
                    .warmArend(offer.getWarmArend())
                    .rooms(offer.getRooms())
                    .floor(offer.getFloor())
                    .description(offer.getDescription())
                    .internet(offer.getInternet())
                    .title(offer.getTitle())
                    .build();
        }
    }
}
