package in.login.controller;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void registerAndLoginTest() throws Exception {

    String requestBody =
        "{" +
            "\"userName\": \"Eliot\",\n" +
            "\"email\": \"eliot@gmail.com\",\n" +
            "\"password\": \"12345678\",\n" +
            "\"gender\": \"MALE\",\n" +
            "\"state\": \"AP\",\n" +
            "\"mobileNumber\": \"9988772222\",\n" +
            "\"address\": \"delsun-toto h-no.23\"\n" + "}";

    mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userName").value(is("Eliot")));

    //test with incorrect password
    mockMvc
        .perform(MockMvcRequestBuilders.get("/auth/login?userName=Eliot&password=11113433")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isUnauthorized());

    //test with correct password
    mockMvc
        .perform(MockMvcRequestBuilders.get("/auth/login?userName=Eliot&password=12345678")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getAllUsersTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/all").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].userName").value(is("Chadwick")));
  }
}