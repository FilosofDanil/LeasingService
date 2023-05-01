package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Searchers;
import org.hibernate.mapping.List;
import org.springframework.data.repository.CrudRepository;

public interface SearchersRepository extends CrudRepository<Searchers,Long> {
    Searchers findSearchersByCredentials(Credentials credentials);
}
