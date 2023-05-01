package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Assignments;
import org.springframework.data.repository.CrudRepository;

public interface AssignmentRepository extends CrudRepository<Assignments, Long> {
}
