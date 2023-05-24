package com.example.wohnungsuchen.controllers;

import com.example.wohnungsuchen.models.auxiliarymodels.AppointmentDeleteModel;
import com.example.wohnungsuchen.models.AppointmentModel;
import com.example.wohnungsuchen.postmodels.AppointmentPostModel;
import com.example.wohnungsuchen.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/")
    public List<AppointmentModel> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments()).getBody();
    }

    @PreAuthorize("hasAuthority('SEARCHER')")
    @GetMapping("/user/{id}")
    public List<AppointmentModel> getAppointmentsAssignedToCertainUser(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsAssignedToCertainUser(id)).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @GetMapping("/leaseholder/{id}")
    public List<AppointmentModel> getAppointmentsCreatedByCertainLeaseholder(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsCreatedByCertainLeaseholder(id)).getBody();
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PostMapping("/{searchers_id}")
    public HttpStatus assignAppointmentToCertainUser(@PathVariable Long searchers_id, @RequestBody String appointment_id) {
        appointmentService.assignAppointmentToCertainUser(searchers_id, Long.parseLong(appointment_id));
        return HttpStatus.CREATED;
    }

    @PreAuthorize("hasAuthority('LEASEHOLDER')")
    @PostMapping("/")
    public ResponseEntity<AppointmentModel> addAppointment(@RequestBody AppointmentPostModel appointmentPostModel) {
        AppointmentModel savedAppointment = appointmentService.addAppointment(appointmentPostModel, SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @PostMapping("/random/{appointment_id}")
    public HttpStatus assignAppointmentForRandomSearchers(@RequestBody Integer count, @PathVariable Long appointment_id) {
        appointmentService.assignAppointmentToRandomUsers(appointment_id, count);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @DeleteMapping("/all")
    public void deleteAppointmentsByOfferAndTime(@RequestBody AppointmentDeleteModel appointmentDeleteModel) {
        appointmentService.deleteAppointmentsByOfferAndTime(appointmentDeleteModel);
    }

    @PutMapping("/{id}")
    public void updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentPostModel appointmentPostModel) {
        appointmentService.updateAppointment(appointmentPostModel, id, SecurityContextHolder.getContext().getAuthentication());
    }

    @PatchMapping
    public void partlyUpdateAppointment() {

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

}
