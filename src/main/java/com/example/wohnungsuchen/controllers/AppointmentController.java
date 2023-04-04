package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auxiliarymodels.AppointmentDeleteModel;
import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.models.AppointmentModel;
import com.example.wohnungsuchen.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    @PreAuthorize("hasAuthority('SEARCHER')")
    @GetMapping("/")
    public List<AppointmentModel> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public List<AppointmentModel> getAppointmentsAssignedToCertainUser(@PathVariable Long id) {
        return appointmentService.getAppointmentsAssignedToCertainUser(id);
    }

    @PreAuthorize("hasAuthority('LODGER')")
    @GetMapping("/{id}")
    public List<AppointmentModel> getAppointmentsCreatedByCertainLodger(@PathVariable Long id) {
       return appointmentService.getAppointmentsCreatedByCertainLodger(id);
    }

    @PreAuthorize("hasAuthority('LODGER')")
    @PostMapping("/")
    public void assignAppointment(){

    }

    @PostMapping("/random")
    public void assignAppointmentForRandomSearchers(){

    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
    }

    @DeleteMapping("/all")
    public void deleteAppointmentsByOfferAndTime(@RequestBody AppointmentDeleteModel appointmentDeleteModel){
        appointmentService.deleteAppointmentsByOfferAndTime(appointmentDeleteModel);
    }

    @PutMapping
    public void updateAppointment(){

    }

    @PatchMapping
    public void partlyUpdateAppointment(){

    }

}
