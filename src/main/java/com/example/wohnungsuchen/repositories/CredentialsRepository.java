package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Credentials;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
    Credentials findCreditsByActivationCode(String activationCode);

    Credentials findByEmail(String email);
}
