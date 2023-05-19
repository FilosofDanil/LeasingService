package com.example.wohnungsuchen.controllers_tests;

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

    }

    @Test
    public void getAppointmentsToCertainUser() throws Exception {

    }

    @Test
    public void getAppointmentsByOffer() throws Exception {

    }

    @Test
    public void getAppointmentsCreatedByCertainLeaseholder() throws Exception {

    }

    @Test
    public void addAppointment() throws Exception {

    }

    @Test
    public void assignAppointmentToCertainUser() throws Exception {

    }

    @Test
    public void assignAppointmentToRandomUser() throws Exception {

    }
}
