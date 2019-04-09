package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.ApplicationTest;
import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.User;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.alexwylsa.Persons.domain.Role.ADMIN;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationTest.class)
@WebAppConfiguration
@EnableAutoConfiguration
//@WebMvcTest(UserController.class)
public class UserServiceTest{

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }
    //get all users
    @Test
    public void getAllUsersTest() throws Exception
    {
        mockMvc.perform(get("/users?page=1&size=5&ascending=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username").exists())
                .andExpect(jsonPath("$.[0].id").isNotEmpty());
    }
    //get one user by id
    @Test
    public void getUsersById() throws Exception
    {
        mockMvc.perform(get("/users/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
    //add user
    @Test
    public void testAddUser() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(ADMIN);
        User user = new User(null, "admin", encoder.encode("123"),true, roles);
        UserService mock = org.mockito.Mockito.mock(UserService.class);
        when(mock.addUser(user)).thenReturn(user);
        mockMvc.perform(get("/users?page=1&size=5&ascending=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username", Matchers.is("admin")))
                .andExpect(jsonPath("$.[0].active", Matchers.is(true)));
                //.andExpect(jsonPath("$.[0].roles", Matchers.equalTo(Arrays.asList("\""+ADMIN+"\""))));
    }

    //update users by id
//    @Test
//    public void updateUsersById() throws Exception {
////        Set<Role> roles = new HashSet<>();
////        roles.add(ADMIN);
////        User user = new User(null, "admin", encoder.encode("123"),true, roles);
////        UserService mock = org.mockito.Mockito.mock(UserService.class);
////        when(mock.addUser(user)).thenReturn(user);
//
//        mockMvc.perform(put("/users/{id}", 1)
//                .content()
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("user"));
//                //.andExpect(jsonPath("$.[0].active").value(false));
//    }

//    @Test
//    public void test_update_user_success() throws Exception {
//        User user = new User(1, "Arya Stark");
//        when(userService.findById(user.getId())).thenReturn(user);
//        doNothing().when(userService).update(user);
//        mockMvc.perform(
//                put("/users/{id}", user.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(user)))
//                .andExpect(status().isOk());
//        verify(userService, times(1)).findById(user.getId());
//        verify(userService, times(1)).update(user);
//        verifyNoMoreInteractions(userService);
//    }


    //delete user by id
    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}",1))
                .andExpect(status().isOk());
    }
}

