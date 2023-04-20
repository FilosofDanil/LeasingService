package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Posted;
import org.springframework.data.repository.CrudRepository;

public interface PostedRepository extends CrudRepository<Posted, Long> {
    Iterable<Posted> findAllByLeaseholderId(Long id);
}