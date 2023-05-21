package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.entities.Leaseholders;
import com.example.wohnungsuchen.entities.Searchers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentsRepository extends CrudRepository<Appointments, Long> {
    List<Appointments> findAppointmentsBySearchers(Searchers searchers);

    List<Appointments> findAppointmentsByLeaseholder(Leaseholders leaseholders);
}
