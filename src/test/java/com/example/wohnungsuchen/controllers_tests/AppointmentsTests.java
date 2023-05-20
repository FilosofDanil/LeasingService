package com.example.wohnungsuchen.controllers_tests;

import com.example.wohnungsuchen.auth.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("applicationtest.properties")
@Sql(value = {"fillindatabase.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"cleandatabase.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AppointmentsTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllAppointments() throws Exception {
        this.mockMvc.perform(get("/api/v1/appointments/").with(user("serhio3347@gmail.com").password("123456789").authorities(Role.SEARCHER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAppointmentsToCertainUser() throws Exception {
        this.mockMvc.perform(get("/api/v1/appointments/user/1").with(user("serhio3347@gmail.com").password("123456789").authorities(Role.SEARCHER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAppointmentsByOffer() throws Exception {

    }

    @Test
    public void getAppointmentsCreatedByCertainLeaseholder() throws Exception {
        this.mockMvc.perform(get("/api/v1/appointments/leaseholder/1").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addAppointment() throws Exception {
        this.mockMvc.perform(post("/api/v1/appointments/").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER))
                        .content("{" +
                                "\"meeting_date\": \"2023-06-04\"," +
                                "\"meeting_time\": \"14:00:00\"," +
                                "\"description\": \"Some text\"," +
                                "\"offer\": 6" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void assignAppointmentToCertainUser() throws Exception {
        this.mockMvc.perform(post("/api/v1/appointments/1").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER))
                        .content("2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void assignAppointmentToRandomUser() throws Exception {
        this.mockMvc.perform(post("/api/v1/appointments/random/3").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER))
                        .content("1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
