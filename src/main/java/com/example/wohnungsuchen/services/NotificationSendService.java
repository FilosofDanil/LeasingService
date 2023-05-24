package com.example.wohnungsuchen.services;

import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.entities.Credentials;
import com.example.wohnungsuchen.repositories.AppointmentsRepository;
import com.example.wohnungsuchen.repositories.AssignmentRepository;
import com.example.wohnungsuchen.services.security.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class NotificationSendService {
    private final AssignmentRepository assignmentRepository;
    private final AppointmentsRepository appointmentsRepository;
    @Autowired
    private MailSender mailSender;

    @Scheduled(fixedDelay = 10000)
    public void remindAll() {
        appointmentsRepository.findAll().forEach(appointments -> {
            if (pastEvent(appointments)) {
                appointmentsRepository.delete(appointments);
            }
            if (compareDates(appointments)) {
                assignmentRepository.findAll().forEach(assignments -> {
                    if (assignments.getAppointment().getId().equals(appointments.getId()) && !assignments.getNotified() && assignments.getSearcher().getNotifications()) {
                        assignments.setNotified(true);
                        assignmentRepository.save(assignments);
                        sendNotification(assignments.getSearcher().getCredentials(), appointments);
                    }
                });
            }
        });
    }

    private void sendNotification(Credentials credentials, Appointments appointments) {
        mailSender.send(credentials.getEmail(), "Notification!", "Dear " + credentials.getProfile_name() + " " + credentials.getSurname() + "\n" +
                "Address: " + appointments.getOffer().getCity() + " " + appointments.getOffer().getAddress() + "\nHi! We're would like to remind you about an nearest appointment. \n"
                + "On which you have been invited by "
                + appointments.getLeaseholder().getCredentials().getProfile_name() + " " + appointments.getLeaseholder().getCredentials().getSurname()
                + "Please come up to the address, which is indicated above"
                + " in " + appointments.getMeeting_date() + " at " + appointments.getMeeting_time() + ". Please, come in 15 minutes advance  \n"
                + "If you wouldn't to get remind notifications, you have an opportunity to disable this function in your profile settings \n"
                + "Respectfully, WohnungSuchen!"
        );
    }

    private boolean pastEvent(Appointments appointments) {
        Date date = new Date();
        return date.after(additionDateAndTime(appointments).getTime());
    }

    private boolean compareDates(Appointments appointments) {
        Date date = new Date();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        return additionDateAndTime(appointments).getTime().getTime() - currentDate.getTime().getTime() <= 86400000;
    }

    private Calendar additionDateAndTime(Appointments appointments) {
        Calendar comparingDate = Calendar.getInstance();
        comparingDate.setTime(appointments.getMeeting_date());
        Time time = appointments.getMeeting_time();
        comparingDate.add(Calendar.HOUR, time.getHours());
        comparingDate.add(Calendar.MINUTE, time.getMinutes());
        comparingDate.add(Calendar.SECOND, time.getSeconds());
        return comparingDate;
    }
}
