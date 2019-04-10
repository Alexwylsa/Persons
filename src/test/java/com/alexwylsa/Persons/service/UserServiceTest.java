package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.ApplicationTest;
import com.alexwylsa.Persons.domain.UserInDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationTest.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class UserServiceTest{

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;
    //convert to string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    //add user
    @Test
    public void addUserTest() throws Exception {
        mockMvc.perform(post("/users")
                .content(asJsonString(new UserInDto("admin", "123")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is("admin")));
    }

    //update users by id
    @Test
    public void updateUsersByIdTest() throws Exception {
        mockMvc.perform(put("/users/{id}", 1)
                .content(asJsonString(new UserInDto("user", "123")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }

    //get all users with parameters
    @Test
    public void getAllUsersTest() throws Exception {
        mockMvc.perform(get("/users?page=1&size=5&ascending=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].username").isNotEmpty());
    }

    //get one user by id
    @Test
    public void getUsersByIdTest() throws Exception {
        mockMvc.perform(get("/users/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    //delete user by id
    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/{id}",1))
                .andExpect(status().isOk());
    }
}

