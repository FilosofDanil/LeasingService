package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Offers;
import com.example.wohnungsuchen.filters.FilterFactory;
import com.example.wohnungsuchen.filters.IFilter;
import com.example.wohnungsuchen.models.OfferModel;
import com.example.wohnungsuchen.postmodels.OfferPostModel;
import com.example.wohnungsuchen.repositories.CredentialsRepository;
import com.example.wohnungsuchen.repositories.ImagesRepository;
import com.example.wohnungsuchen.repositories.LeaseholdersRepository;
import com.example.wohnungsuchen.repositories.OffersRepository;
import com.example.wohnungsuchen.sorters.offersorters.Direction;
import com.example.wohnungsuchen.sorters.offersorters.OfferComparatorsFactory;
import com.example.wohnungsuchen.sorters.offersorters.OfferDateComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OffersRepository offersRepository;
    private final CredentialsRepository credentialsRepository;
    private final ImagesRepository imagesRepository;
    private final LeaseholdersRepository leaseholdersRepository;

    public List<OfferModel> getAllOffers(String filter, String sort, String direction) throws ParseException {
        List<Offers> list = doFilter(getOffersList(), detectFiltrationMethods(divideString(filter)), getParametersMap(divideString(filter)));
        sort(list, sort, direction);
        return list.stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<OfferModel> getAllOffers(String sort, String direction) {
        List<Offers> list = getOffersList();
        sort(list, sort, direction);
        return list.stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    private void sort(List<Offers> list, String sort, String direction) {
        if (sort == null) {
            list.sort(new OfferDateComparator(Direction.DESC));
        } else {
            OfferComparatorsFactory offerComparatorsFactory = new OfferComparatorsFactory();
            list.sort(offerComparatorsFactory.getSorter(sort + direction));
        }
    }

    public Page<OfferModel> getAllOffersPage(Pageable pageable) {
        return offersRepository.findAll(pageable).map(OfferMapper::toModel);
    }

    public List<OfferModel> getAllCreatedOffersByLeaseholderId(Long leaseholder_id) {
        return getOffersList()
                .stream()
                .filter(offers -> offers.getLeaseholders().getId().equals(leaseholder_id))
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
    }

    public Page<OfferModel> getAllOffers(Pageable pageable, String filter) throws ParseException {
        List<Offers> list = doFilter(getOffersList(), detectFiltrationMethods(divideString(filter)), getParametersMap(divideString(filter)));
        List<OfferModel> offerModelList = list.stream()
                .map(OfferMapper::toModel)
                .collect(Collectors.toList());
        return new PageImpl<>(offerModelList, pageable, offerModelList.size());
    }

    public Offers addOffer(OfferPostModel offerPostModel, Authentication auth) {
        Offers offer = OfferMapper.toOffer(offerPostModel);
        imagesRepository.findAll().forEach(images -> {
            if (offerPostModel.getLink().equals(images.getLink())) {
                offer.setImage(images);
            }
        });
        offer.setLeaseholders(getLeaseholderByName(auth));
        Date postDate = new Date();
        offer.setPost_date(postDate);
        offersRepository.save(offer);
        return offer;
    }

    private List<Offers> doFilter(List<Offers> offers, String[] filter, HashMap<String, String> params) throws ParseException {
        FilterFactory filterFactory = new FilterFactory();
        for (String s : filter) {
            IFilter filterImpl = filterFactory.getFilter(s);
            offers = filterImpl.doFilter(params, offers);
        }
        return offers;
    }

    private HashMap<String, String> getParametersMap(String[] s) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 1; i < s.length; i++) {
            String[] s_pars = s[i].split("=");
            map.put(s_pars[0], s_pars[1]);
        }
        return map;
    }

    private String[] divideString(String filter) {
        return filter.split("\\?");
    }

    private String[] detectFiltrationMethods(String[] s) {
        return s[0].split(",");
    }

    public void deleteOffer(Long id) {
        offersRepository.deleteById(id);
    }

    public void updateOffer(Long id, OfferPostModel offer, Authentication auth) {
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
                    if (!getLeaseholderByName(auth).getId().equals(offers.getLeaseholders().getId())) {
                        throw new RuntimeException();
                    }
                    offers.setLeaseholders(getLeaseholderByName(auth));
                    return offersRepository.save(offers);
                })
                .orElseGet(() -> {
                    Offers offers = OfferMapper.toOffer(offer);
                    offers.setId(id);
                    offers.setLeaseholders(getLeaseholderByName(auth));
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

    private Leaseholders getLeaseholderByName(Authentication auth) {
        String username = (String)auth.getPrincipal();
        List<Credentials> credentials = new ArrayList<>();
        credentialsRepository.findAll().forEach(credentials::add);
        if (credentials.isEmpty()) {
            throw new NullPointerException();
        }
        Credentials cred = credentials.stream().filter(credentials1 -> credentials1.getEmail().equals(username)).findFirst().get();
        return leaseholdersRepository.findByCredentials(cred);
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
                    .likes_link("http://localhost:8080/api/likes/v1/" + offer.getId())
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
