package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.auxiliarymodels.AppointmentDeleteModel;
import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.models.AppointmentModel;
import com.example.wohnungsuchen.postmodels.AppointmentPostModel;
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
    @GetMapping("/")
    public List<AppointmentModel> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PreAuthorize("hasAuthority('SEARCHER')")
    @GetMapping("/user/{id}")
    public List<AppointmentModel> getAppointmentsAssignedToCertainUser(@PathVariable Long id) {
        return appointmentService.getAppointmentsAssignedToCertainUser(id);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/Leaseholder/{id}")
    public List<AppointmentModel> getAppointmentsCreatedByCertainLeaseholder(@PathVariable Long id) {
       return appointmentService.getAppointmentsCreatedByCertainLeaseholder(id);
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
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

    @PutMapping("/{id}")
    public void updateAppointment(@PathVariable Long id, @RequestBody AppointmentPostModel appointmentPostModel){
        appointmentService.updateAppointment(appointmentPostModel, id);
    }

    @PatchMapping
    public void partlyUpdateAppointment(){

    }

}
