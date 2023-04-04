package com.example.wohnungsuchen.mappers;

import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.models.AppointmentModel;

public class AppointmentMapper {



    public static AppointmentModel toModel(Appointments appointment){
        return AppointmentModel.builder()
                .id(appointment.getId())
                .city(appointment.getOffer().getCity())
                .lodger_name(appointment.getLodger().getCredits().getProfile_name())
                .lodger_surname(appointment.getLodger().getCredits().getSurname())
                .address(appointment.getOffer().getAddress())
                .description(appointment.getDescription())
                .meeting_time(appointment.getMeeting_time())
                .meeting_date(appointment.getMeeting_date())
                .offer_title(appointment.getOffer().getTitle())
                .build();
    }
}
