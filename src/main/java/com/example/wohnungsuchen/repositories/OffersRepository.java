package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Offers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OffersRepository extends CrudRepository<Offers, Long> {
    Page<Offers> findAll(Pageable pageable);
}
