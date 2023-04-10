package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auxiliarymodels.AppointmentDeleteModel;
import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.models.AppointmentModel;
import com.example.wohnungsuchen.postmodels.AppointmentPostModel;
import com.example.wohnungsuchen.repositories.AppointmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentsRepository appointmentsRepository;

    public List<AppointmentModel> getAllAppointments() {
        return getAppointmentList().stream().map(AppointmentMapper::toModel).collect(Collectors.toList());
    }

    public List<AppointmentModel> getAppointmentsAssignedToCertainUser(Long id) {
        return getAppointmentList()
                .stream()
                .filter(appointmentModel -> appointmentModel.getSearcher().getId().equals(id))
                .map(AppointmentMapper::toModel)
                .collect(Collectors.toList());

    }

    public List<AppointmentModel> getAppointmentsCreatedByCertainLeaseholder(Long id) {
        return getAppointmentList().stream()
                .filter(appointment -> appointment.getLeaseholder().getId().equals(id))
                .map(AppointmentMapper::toModel)
                .collect(Collectors.toList());
    }

    public void deleteAppointment(Long id) {
        appointmentsRepository.deleteById(id);
    }

    public void deleteAppointmentsByOfferAndTime(AppointmentDeleteModel appointmentDeleteModel) {
        appointmentsRepository.deleteAll(getAppointmentList()
                .stream()
                .filter(appointment -> appointment.getOffer().equals(appointmentDeleteModel.getOffer()))
                .collect(Collectors.toList())
                .stream()
                .filter(appointment -> appointment.getMeeting_time().equals(appointmentDeleteModel.getMeeting_time()))
                .collect(Collectors.toList())
                .stream()
                .filter(appointment -> appointment.getMeeting_date().equals(appointmentDeleteModel.getMeeting_date()))
                .collect(Collectors.toList()));
    }

    public void updateAppointment(AppointmentPostModel appointmentPostModel, Long id) {
        appointmentsRepository.findById(id)
                .map(appointments -> {
                    appointments.setDescription(appointmentPostModel.getDescription());
                    appointments.setMeeting_time(appointmentPostModel.getMeeting_time());
                    appointments.setMeeting_date(appointmentPostModel.getMeeting_date());
                    appointments.setOffer(appointmentPostModel.getOffer());
                    appointments.setLeaseholder(appointmentPostModel.getLeaseholder());
                    appointments.setSearcher(appointmentPostModel.getSearcher());
                    return appointmentsRepository.save(appointments);
                })
                .orElseGet(() -> {
                    Appointments appointment = AppointmentMapper.toEntity(appointmentPostModel);
                    return appointmentsRepository.save(appointment);
                });
    }

    public void partlyUpdateAppointment() {

    }

    private List<Appointments> getAppointmentList() {
        List<Appointments> appointments = new ArrayList<>();
        appointmentsRepository.findAll().forEach(appointments::add);
        return appointments;
    }

    static class AppointmentMapper {
        private static AppointmentModel toModel(Appointments appointment) {
            return AppointmentModel.builder()
                    .id(appointment.getId())
                    .city(appointment.getOffer().getCity())
                    .leaseholder_name(appointment.getLeaseholder().getCredits().getProfile_name())
                    .leaseholder_surname(appointment.getLeaseholder().getCredits().getSurname())
                    .address(appointment.getOffer().getAddress())
                    .description(appointment.getDescription())
                    .meeting_time(appointment.getMeeting_time())
                    .meeting_date(appointment.getMeeting_date())
                    .offer_title(appointment.getOffer().getTitle())
                    .build();
        }

        private static Appointments toEntity(AppointmentPostModel appointmentPostModel) {
            return Appointments.builder()
                    .description(appointmentPostModel.getDescription())
                    .meeting_time(appointmentPostModel.getMeeting_time())
                    .meeting_date(appointmentPostModel.getMeeting_date())
                    .offer(appointmentPostModel.getOffer())
                    .leaseholder(appointmentPostModel.getLeaseholder())
                    .searcher(appointmentPostModel.getSearcher())
                    .build();
        }
    }

}
