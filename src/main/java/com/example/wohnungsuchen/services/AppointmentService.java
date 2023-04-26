package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.auxiliarymodels.AppointmentDeleteModel;
import com.example.wohnungsuchen.entities.*;
import com.example.wohnungsuchen.models.AppointmentModel;
import com.example.wohnungsuchen.postmodels.AppointmentPostModel;
import com.example.wohnungsuchen.repositories.*;
import com.example.wohnungsuchen.security.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentsRepository appointmentsRepository;
    private final SearchersRepository searchersRepository;
    private final OffersRepository offersRepository;
    private final LeaseholdersRepository leaseholdersRepository;
    private final CredentialsRepository credentialsRepository;
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

    public Appointments addAppointment(AppointmentPostModel appointmentPostModel, Authentication auth) {
        Appointments appointments = AppointmentMapper.toEntity(appointmentPostModel, offersRepository, leaseholdersRepository);
        appointments.setLeaseholder(getLeaseholderByName(auth));
        appointmentsRepository.save(appointments);
        return appointments;
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

    public void assignAppointmentToRandomUsers(Long appointment_id, Integer count) {
        assignSearchersFromList(appointment_id, generateRandomList(count));
    }

    public void deleteAppointment(Long id) {
        appointmentsRepository.deleteById(id);
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

    public void updateAppointment(AppointmentPostModel appointmentPostModel, Long id, Authentication auth) {
        appointmentsRepository.findById(id)
                .map(appointments -> {
                    appointments.setLeaseholder(getLeaseholderByName(auth));
                    appointments.setDescription(appointmentPostModel.getDescription());
                    appointments.setMeeting_time(Time.valueOf(appointmentPostModel.getMeeting_time()));
                    appointments.setMeeting_date(appointmentPostModel.getMeeting_date());
                    appointments.setOffer(offersRepository.findById(appointmentPostModel.getOffer()).get());
                    return appointmentsRepository.save(appointments);
                })
                .orElseGet(() -> {
                    Appointments appointment = AppointmentMapper.toEntity(appointmentPostModel, offersRepository, leaseholdersRepository);
                    return appointmentsRepository.save(appointment);
                });
    }

    private Leaseholders getLeaseholderByName(Authentication auth) {
        String name = auth.getName();
        List<Credentials> credentials = new ArrayList<>();
        credentialsRepository.findAll().forEach(credentials::add);
        Credentials cred = credentials.stream().filter(credentials1 -> credentials1.getProfile_name().equals(name)).findFirst().get();
        return leaseholdersRepository.findByCredentials(cred);
    }

    public void partlyUpdateAppointment() {

    }

    private List<Appointments> getAppointmentList() {
        List<Appointments> appointments = new ArrayList<>();
        appointmentsRepository.findAll().forEach(appointments::add);
        return appointments;
    }

    private void assignSearchersFromList(Long appointment_id, List<Searchers> searchers) {
        if (appointmentsRepository.findById(appointment_id).isEmpty()) {
            throw new NullPointerException();
        }
        Appointments appointment = appointmentsRepository.findById(appointment_id).get();
        for (Searchers searcher : searchers) {
            appointment.getSearchers().add(searcher);
        }
        appointmentsRepository.save(appointment);
    }

    private List<Searchers> generateRandomList(int count) {
        List<Searchers> searchers = new ArrayList<>();
        searchersRepository.findAll().forEach(searchers::add);
        List<Searchers> randomList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int r = (int) (Math.random() * searchers.size());
            randomList.add(searchers.get(r));
            searchers.remove(r);
        }
        return randomList;
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

        private static Appointments toEntity(AppointmentPostModel appointmentPostModel, OffersRepository offersRepository, LeaseholdersRepository leaseholdersRepository) {
            return Appointments.builder()
                    .description(appointmentPostModel.getDescription())
                    .meeting_time(Time.valueOf(appointmentPostModel.getMeeting_time()))
                    .meeting_date(appointmentPostModel.getMeeting_date())
                    .offer(offersRepository.findById(appointmentPostModel.getOffer()).get())
                    .build();
        }
    }

}
