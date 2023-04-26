package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Leaseholders;
import org.springframework.data.repository.CrudRepository;

public interface LeaseholdersRepository extends CrudRepository<Leaseholders, Long> {
    Leaseholders findByCredentials(Credentials credentials);
}
