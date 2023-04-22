package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auxiliarymodels.AppointmentDeleteModel;
import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.entities.Searchers;
import com.example.wohnungsuchen.models.AppointmentModel;
import com.example.wohnungsuchen.postmodels.AppointmentPostModel;
import com.example.wohnungsuchen.repositories.AppointmentsRepository;
import com.example.wohnungsuchen.repositories.CredentialsRepository;
import com.example.wohnungsuchen.repositories.SearchersRepository;
import com.example.wohnungsuchen.security.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentsRepository appointmentsRepository;
    private final CredentialsRepository credentialsRepository;
    private final SearchersRepository searchersRepository;
    @Autowired
    private MailSender mailSender;

    public List<AppointmentModel> getAllAppointments() {
        return getAppointmentList().stream().map(AppointmentMapper::toModel).collect(Collectors.toList());
    }

    public List<AppointmentModel> getAppointmentsAssignedToCertainUser(Long id) {
        return getAppointmentList()
                .stream()
                .filter(appointmentModel -> appointmentModel.getSearchers().contains(searchersRepository.findById(id).get()))
                .map(AppointmentMapper::toModel)
                .collect(Collectors.toList());

    }

    public List<AppointmentModel> getAppointmentsCreatedByCertainLeaseholder(Long id) {
        return getAppointmentList().stream()
                .filter(appointment -> appointment.getLeaseholder().getId().equals(id))
                .map(AppointmentMapper::toModel)
                .collect(Collectors.toList());
    }

    public void addAppointment(AppointmentPostModel appointmentPostModel) {
        appointmentsRepository.save(AppointmentMapper.toEntity(appointmentPostModel));
    }

    public void assignAppointmentToCertainUser(Long searchers_id, Long appointment_id) {
        if (searchersRepository.findById(searchers_id).isEmpty() || appointmentsRepository.findById(appointment_id).isEmpty()) {
            throw new NullPointerException();
        }
        Searchers searcher = searchersRepository.findById(searchers_id).get();
        Appointments appointment = appointmentsRepository.findById(appointment_id).get();
        appointment.getSearchers().add(searcher);
        appointmentsRepository.save(appointment);
        sendMessage(searcher.getCredentials(), appointment);
    }

    private void sendMessage(Credentials credentials, Appointments appointments) {
        mailSender.send(credentials.getEmail(), "Making an appointment", "Dear " + credentials.getProfile_name() + " " + credentials.getSurname() + "\n" +
                "Address: " + appointments.getOffer().getCity() + " " + appointments.getOffer().getAddress() + "\nHi! Congratulations, you have been invited by "
                + appointments.getLeaseholder().getCredentials().getProfile_name() + " " + appointments.getLeaseholder().getCredentials().getSurname() + " on an apartment revision. \n" +
                "So, we're expecting, that you will be there in " + appointments.getMeeting_date() + " at " + appointments.getMeeting_time() + ". Please, come in 15 minutes advance  \n" +
                "In the day before, we're going to remind you about this appointment, and send short mail to the your email, which staying in your profile \n" +
                "If you wouldn't to get remind notifications, you have an opportunity to disable this function in your profile settings \n" +
                "Respectfully, WohnungSuchen!"
        );
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
                    .leaseholder_name(appointment.getLeaseholder().getCredentials().getProfile_name())
                    .leaseholder_surname(appointment.getLeaseholder().getCredentials().getSurname())
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
                    .build();
        }
    }

}
