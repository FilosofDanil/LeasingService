package com.example.wohnungsuchen.controllers_tests;

import com.example.wohnungsuchen.entities.Appointments;
import com.example.wohnungsuchen.repositories.AppointmentsRepository;
import com.example.wohnungsuchen.repositories.AssignmentRepository;
import com.example.wohnungsuchen.services.NotificationSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("applicationtest.properties")
@Sql(value = {"fillindatabase.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"cleandatabase.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class NotificationServiceTests {
    @Autowired
    private NotificationSendService service;

    @MockBean
    private AssignmentRepository assignmentRepository;

    @MockBean
    private AppointmentsRepository appointmentsRepository;

    @MockBean
    private MailSender mailSender;

    @Test
    public void remindAll() {

    }
}
