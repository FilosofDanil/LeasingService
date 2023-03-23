package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Appointments;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentsRepository extends CrudRepository<Appointments, Long> {
}
