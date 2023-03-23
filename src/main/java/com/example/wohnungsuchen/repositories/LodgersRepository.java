package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Lodgers;
import org.springframework.data.repository.CrudRepository;

public interface LodgersRepository extends CrudRepository<Lodgers, Long> {
}
