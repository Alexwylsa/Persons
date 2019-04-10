package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.ApplicationTest;
import com.alexwylsa.Persons.domain.DepartmentInDto;
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
public class DepartmentServiceTest {

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
    //get all department with parameters test
    @Test
    public void getAllDepartmentsTest() throws Exception {
        mockMvc.perform(get("/departments?page=1&size=5&ascending=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].name").isNotEmpty());
    }
    //get one department by id test
    @Test
    public void getDepartmentByIdTest() throws Exception{
        mockMvc.perform(get("/departments/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
    //add new department test
    @Test
    public void addDepartmentTest() throws Exception {
        mockMvc.perform(post("/departments")
                .content(asJsonString(new DepartmentInDto("Main Department", 1L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Main Department")))
                .andExpect(jsonPath("$.bossId", Matchers.is(1)));
    }
    //update department test
    @Test
    public void updateDepartmentTest() throws Exception {
        mockMvc.perform(put("/departments/{id}", 2)
                .content(asJsonString(new DepartmentInDto("Not", 1L)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Not Main Department"))
                .andExpect(jsonPath("$.bossId").value(1));
    }
    //delete department test
    @Test
    public void deleteDepartmentTest() throws Exception {
        mockMvc.perform(delete("/departments/{id}",1))
                .andExpect(status().isOk());
    }
}
