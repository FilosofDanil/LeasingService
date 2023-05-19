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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("applicationtest.properties")
@Sql(value = {"fillindatabase.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"cleandatabase.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LikedTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void likeTest() throws Exception {
        this.mockMvc.perform(post("http://localhost:8080/api/v1/likes/7").with(user("serhio3347@gmail.com").password("123456789").authorities(Role.SEARCHER))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void dislikeTest() throws Exception{
        this.mockMvc.perform(delete("http://localhost:8080/api/v1/likes/1").with(user("serhio3347@gmail.com").password("123456789").authorities(Role.SEARCHER))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getAllLikesByUser() throws Exception{
        this.mockMvc.perform(get("http://localhost:8080/api/v1/likes/").with(user("serhio3347@gmail.com").password("123456789").authorities(Role.SEARCHER)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLikesByOffer() throws Exception{
        this.mockMvc.perform(get("/api/v1/offers/7").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
