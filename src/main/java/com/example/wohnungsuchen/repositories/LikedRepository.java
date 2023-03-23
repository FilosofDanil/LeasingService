package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Liked;
import org.springframework.data.repository.CrudRepository;

public interface LikedRepository extends CrudRepository<Liked,Long> {
}
