package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Liked;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface LikedRepository extends CrudRepository<Liked, Long> {
    List<Liked> findAllByOfferId(Long id);
}
