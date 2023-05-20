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

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("applicationtest.properties")
@Sql(value = {"fillindatabase.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"cleandatabase.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OffersTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/api/v1/offers/"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllOffersTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/offers//").with(user("dru"))
                        .param("page", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":11,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":750.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":2,\"area\":45.0,\"internet\":true,\"balkoon\":true,\"floor\":11,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/11\",\"self_link\":\"http://localhost:8080/api/v1/offers/11\"},{\"id\":12,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":800.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":50.0,\"internet\":true,\"balkoon\":true,\"floor\":12,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/12\",\"self_link\":\"http://localhost:8080/api/v1/offers/12\"}")));
    }

    @Test
    public void getAllOffersTestWithOneFilter() throws Exception {
        this.mockMvc.perform(get("/api/v1/offers/").with(user("dru"))
                        .param("filter", "city?city=Hamburg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":2,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":300.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":2,\"area\":32.5,\"internet\":true,\"balkoon\":true,\"floor\":2,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/2\",\"self_link\":\"http://localhost:8080/api/v1/offers/2\"},{\"id\":3,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":350.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":35.0,\"internet\":true,\"balkoon\":true,\"floor\":3,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/3\",\"self_link\":\"http://localhost:8080/api/v1/offers/3\"},{\"id\":6,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":500.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":50.0,\"internet\":true,\"balkoon\":true,\"floor\":6,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/6\",\"self_link\":\"http://localhost:8080/api/v1/offers/6\"},{\"id\":7,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":550.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":1,\"area\":30.0,\"internet\":true,\"balkoon\":true,\"floor\":7,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/7\",\"self_link\":\"http://localhost:8080/api/v1/offers/7\"},{\"id\":9,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":650.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":35.0,\"internet\":true,\"balkoon\":true,\"floor\":9,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/9\",\"self_link\":\"http://localhost:8080/api/v1/offers/9\"}")));
    }

    @Test
    public void getAllOffersTestWithOneFilterAndSort() throws Exception {
        this.mockMvc.perform(get("/api/v1/offers/").with(user("dru"))
                        .param("filter", "city?city=Hamburg")
                        .param("sort", "area"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":6,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":500.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":50.0,\"internet\":true,\"balkoon\":true,\"floor\":6,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/6\",\"self_link\":\"http://localhost:8080/api/v1/offers/6\"},{\"id\":12,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":800.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":50.0,\"internet\":true,\"balkoon\":true,\"floor\":12,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/12\",\"self_link\":\"http://localhost:8080/api/v1/offers/12\"},{\"id\":11,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":750.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":2,\"area\":45.0,\"internet\":true,\"balkoon\":true,\"floor\":11,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/11\",\"self_link\":\"http://localhost:8080/api/v1/offers/11\"},{\"id\":3,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":350.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":35.0,\"internet\":true,\"balkoon\":true,\"floor\":3,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/3\",\"self_link\":\"http://localhost:8080/api/v1/offers/3\"},{\"id\":9,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":650.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":35.0,\"internet\":true,\"balkoon\":true,\"floor\":9,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/9\",\"self_link\":\"http://localhost:8080/api/v1/offers/9\"}")));
    }

    @Test
    public void getAllOffersTestWithOneSort() throws Exception {
        this.mockMvc.perform(get("/api/v1/offers/").with(user("dru"))
                        .param("sort", "price")
                        .param("direction", "ASC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":250.0,\"description\":\"Some text\",\"city\":\"Vinitsya\",\"address\":\"Gercena35\",\"rooms\":1,\"area\":30.0,\"internet\":true,\"balkoon\":true,\"floor\":1,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/1\",\"self_link\":\"http://localhost:8080/api/v1/offers/1\"},{\"id\":2,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":300.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":2,\"area\":32.5,\"internet\":true,\"balkoon\":true,\"floor\":2,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/2\",\"self_link\":\"http://localhost:8080/api/v1/offers/2\"},{\"id\":3,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":350.0,\"description\":\"Some text\",\"city\":\"Hamburg\",\"address\":\"Gercena35\",\"rooms\":3,\"area\":35.0,\"internet\":true,\"balkoon\":true,\"floor\":3,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/3\",\"self_link\":\"http://localhost:8080/api/v1/offers/3\"},{\"id\":4,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":400.0,\"description\":\"Some text\",\"city\":\"Vinitsya\",\"address\":\"Gercena35\",\"rooms\":1,\"area\":40.0,\"internet\":true,\"balkoon\":true,\"floor\":4,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/4\",\"self_link\":\"http://localhost:8080/api/v1/offers/4\"},{\"id\":5,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":450.0,\"description\":\"Some text\",\"city\":\"Vinitsya\",\"address\":\"Gercena35\",\"rooms\":2,\"area\":45.0,\"internet\":true,\"balkoon\":true,\"floor\":5,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/5\",\"self_link\":\"http://localhost:8080/api/v1/offers/5\"}")));
    }

    @Test
    public void getOfferById() throws Exception {
        this.mockMvc.perform(get("/api/v1/offers/1").with(user("dru")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"title\":\"House of the rising sun\",\"postdate\":\"2023-05-15T22:00:00.000+00:00\",\"cold_arend\":100.0,\"warm_arend\":250.0,\"description\":\"Some text\",\"city\":\"Vinitsya\",\"address\":\"Gercena35\",\"rooms\":1,\"area\":30.0,\"internet\":true,\"balkoon\":true,\"floor\":1,\"image_link\":\"base.png\",\"likes_link\":\"http://localhost:8080/api/likes/v1/1\",\"self_link\":\"http://localhost:8080/api/v1/offers/1\"}")));
    }

    @Test
    public void addOfferWithMistakenRole() throws Exception {
        this.mockMvc.perform(post("/api/v1/offers/").with(user("serhio3347@gmail.com").password("123456789").authorities(Role.SEARCHER))
                        .content("{" +
                                "\"title\":\"House of the rising sun\"," +
                                "\"cold_arend\":77.7," +
                                "\"warm_arend\":777.77," +
                                "\"description\":\"Some text\"," +
                                "\"city\":\"Vinitsya\"," +
                                "\"address\":\"Vladimirskiy Central\"," +
                                "\"rooms\":2," +
                                "\"area\":35.2," +
                                "\"internet\":true," +
                                "\"balkoon\":true," +
                                "\"floor\":2," +
                                "\"image_link\":\"base.png\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void addOffer() throws Exception {
        this.mockMvc.perform(post("/api/v1/offers/").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER))
                        .content("{\"title\":\"House of the rising sun\"," +
                                "\"cold_arend\":77.7," +
                                "\"warm_arend\":777.77," +
                                "\"description\":\"Some text\"," +
                                "\"city\":\"Vinitsya\"," +
                                "\"address\":\"Vladimirskiy Central\"," +
                                "\"rooms\":2," +
                                "\"area\":35.2," +
                                "\"internet\":true," +
                                "\"balkoon\":true," +
                                "\"floor\":2," +
                                "\"image_link\":\"base.png\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateOffer() throws Exception {
        this.mockMvc.perform(put("/api/v1/offers/1").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER))
                        .content("{\"title\":\"House of the rising sun\"," +
                                "\"cold_arend\":77.7," +
                                "\"warm_arend\":777.77," +
                                "\"description\":\"Some text\"," +
                                "\"city\":\"Vinitsya\"," +
                                "\"address\":\"Vladimirskiy Central\"," +
                                "\"rooms\":2," +
                                "\"area\":35.2," +
                                "\"internet\":true," +
                                "\"balkoon\":true," +
                                "\"floor\":2," +
                                "\"image_link\":\"base.png\"" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void partlyUpdateOffer() throws Exception {
        this.mockMvc.perform(patch("/api/v1/offers/3").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER))
                        .content("{" +
                                "\"address\":\"Gercena 57\"," +
                                "\"rooms\":1," +
                                "\"area\":40.1" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOffer() throws Exception {
        this.mockMvc.perform(delete("/api/v1/offers/10").with(user("uspehagora@gmail.com").password("123456789").authorities(Role.LEASEHOLDER)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
