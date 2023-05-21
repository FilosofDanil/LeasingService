package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Searchers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SearchersRepository extends CrudRepository<Searchers, Long> {
    Searchers findSearchersByCredentials(Credentials credentials);

    @Query(value = "select s.id, s.city, s.credit_id, s.notifications from searchers s join assignments a on appointment_id = :appointmentId", nativeQuery = true)
    Iterable<Searchers> findAllByAssignments(Integer appointmentId);
}
