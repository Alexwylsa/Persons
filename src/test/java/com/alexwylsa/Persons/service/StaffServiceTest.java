package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.ApplicationTest;
import com.alexwylsa.Persons.domain.StaffInDto;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationTest.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class StaffServiceTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

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

    @Test
    public void getAllStaffTest() throws Exception {
        mockMvc.perform(get("/staff?page=1&size=5&ascending=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].age").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[0].lastName").isNotEmpty())
                .andExpect(jsonPath("$.[0].mail").isNotEmpty())
                .andExpect(jsonPath("$.[0].sex").isNotEmpty());
    }

    @Test
    public void getStaff() {
    }

    @Test
    public void addStaffTest() throws Exception {
        mockMvc.perform(post("/staff/")
                .content(asJsonString(new StaffInDto("21","alex","wylsa","alwy@gmail.com","male",1L,1L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", Matchers.is("21")))
                .andExpect(jsonPath("$.firstName", Matchers.is("alex")))
                .andExpect(jsonPath("$.lastName", Matchers.is("wylsa")))
                .andExpect(jsonPath("$.mail", Matchers.is("alwy@gmail.com")))
                .andExpect(jsonPath("$.sex", Matchers.is("male")))
                .andExpect(jsonPath("$.department_id", Matchers.is(1)))
                .andExpect(jsonPath("$.user_id", Matchers.is(1)));
    }

    @Test
    public void updateStaff() {
    }

    @Test
    public void deleteStaff() {
    }
}
