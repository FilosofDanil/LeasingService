package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Posted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostedRepository extends CrudRepository<Posted, Long> {
}