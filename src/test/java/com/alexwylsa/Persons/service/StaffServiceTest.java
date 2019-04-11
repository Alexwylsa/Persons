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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationTest.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class StaffServiceTest {

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
    //get all staff test with parameters
    @Test
    public void getAllStaffTest() throws Exception {
        mockMvc.perform(get("/staff?page=1&size=5&ascending=1")
                .accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].age").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[0].lastName").isNotEmpty())
                .andExpect(jsonPath("$.[0].mail").isNotEmpty())
                .andExpect(jsonPath("$.[0].sex").isNotEmpty());
    }
    //get one stuff by id test
    @Test
    public void getStaffByIdTest() throws Exception {
        mockMvc.perform(get("/staff/{id}", 9)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.age").value("21"))
                .andExpect(jsonPath("$.firstName").value("alex"))
                .andExpect(jsonPath("$.lastName").value("wylsa"))
                .andExpect(jsonPath("$.mail").value("alwy@gmail.com"))
                .andExpect(jsonPath("$.sex").value("male"));
    }
    //add new staff test
    @Test
    public void addStaffTest() throws Exception {
        mockMvc.perform(post("/staff").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new StaffInDto("21","alex","wylsa","alwy@gmail.com",
                        "male",3L,6L)))
                //.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", Matchers.is("21")))
                .andExpect(jsonPath("$.firstName", Matchers.is("alex")))
                .andExpect(jsonPath("$.lastName", Matchers.is("wylsa")))
                .andExpect(jsonPath("$.mail", Matchers.is("alwy@gmail.com")))
                .andExpect(jsonPath("$.sex", Matchers.is("male")));

    }
    //update stuff test
    @Test
    public void updateStaff() throws Exception {
        mockMvc.perform(put("/staff/{id}", 9).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new StaffInDto("21","xela","rysla","alwy@gmail.com",
                        "male",3L,6L)))
                //.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.age").value("21"))
                .andExpect(jsonPath("$.firstName").value("xela"))
                .andExpect(jsonPath("$.lastName").value("rysla"))
                .andExpect(jsonPath("$.mail").value("alwy@gmail.com"))
                .andExpect(jsonPath("$.sex").value("male"));
    }
    //delete stuff test
    @Test
    public void deleteStaff() throws Exception {
        mockMvc.perform(delete("/staff/{id}",9))
                .andExpect(status().isOk());
    }
}
