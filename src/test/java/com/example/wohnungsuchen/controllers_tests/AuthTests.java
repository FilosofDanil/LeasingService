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
@Sql(value = {"create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"cleandatabase.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void signUp() throws Exception {
        this.mockMvc.perform(post("http://localhost:8080/api/auth/signup")
                .content("{\"email\": \"tagip23196@glumark.com\", \"password\":\"123456789\",\"password\":\"123456789\",\"profile_name\":\"Vasya\", \"surname\":\"Pupkin\", \"date_of_birth\":\"2004-03-30\", \"phone\":\"+380968199999\", \"role\":\"SEARCHER\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(post("http://localhost:8080/api/auth/login")
                .content("{\"login\": \"serhio3347@gmail.com\", \"password\":\"123456789\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void send() throws Exception {
        this.mockMvc.perform(post("http://localhost:8080/api/auth/send")
                .content("{\"email\": \"serhio3347@gmail.com\", \"password\":\"123456789\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isNotAcceptable()).andDo(print());
    }
}
